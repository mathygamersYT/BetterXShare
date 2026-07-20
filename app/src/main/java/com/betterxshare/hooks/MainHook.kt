package com.betterxshare.hooks

import android.content.ClipData
import android.content.Context
import android.content.Intent
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {

    companion object {
        private const val TAG = "BetterXShare"

        // Regex patterns for Twitter/X URLs
        private val TWITTER_URL_PATTERN = Regex(
            """https?://(?:twitter\.com|x\.com)(/\w+/status/\d+(?:\?.*)?)""",
            RegexOption.IGNORE_CASE
        )

        private const val REPLACEMENT_DOMAIN = "https://vxtwitter.com"
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.twitter.android") return

        XposedBridge.log("$TAG: Hooking Twitter/X package")

        hookClipboardManager(lpparam)
        hookShareIntent(lpparam)
    }

    /**
     * Hooks ClipboardManager.setPrimaryClip to intercept "Copy link" action
     */
    private fun hookClipboardManager(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            val clipboardManagerClass = XposedHelpers.findClass(
                "android.content.ClipboardManager",
                lpparam.classLoader
            )

            XposedHelpers.findAndHookMethod(
                clipboardManagerClass,
                "setPrimaryClip",
                ClipData::class.java,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        try {
                            val clipData = param.args[0] as? ClipData ?: return
                            val text = clipData.getItemAt(0)?.text?.toString() ?: return

                            val modifiedText = replaceTwitterUrls(text)
                            if (modifiedText != text) {
                                val newClipData = ClipData.newPlainText(
                                    clipData.description.label,
                                    modifiedText
                                )
                                param.args[0] = newClipData
                                XposedBridge.log("$TAG: Replaced clipboard URL: $text -> $modifiedText")
                            }
                        } catch (e: Exception) {
                            XposedBridge.log("$TAG: Error in clipboard hook: ${e.message}")
                        }
                    }
                }
            )
            XposedBridge.log("$TAG: ClipboardManager hook installed successfully")
        } catch (e: Exception) {
            XposedBridge.log("$TAG: Failed to hook ClipboardManager: ${e.message}")
        }
    }

    /**
     * Hooks Intent.putExtra overloads to intercept "Share link" action
     */
    private fun hookShareIntent(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            val intentClass = XposedHelpers.findClass(
                "android.content.Intent",
                lpparam.classLoader
            )

            // Hook putExtra(String, String)
            XposedHelpers.findAndHookMethod(
                intentClass,
                "putExtra",
                String::class.java,
                String::class.java,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        try {
                            val key = param.args[0] as? String ?: return
                            val value = param.args[1] as? String ?: return

                            val modifiedValue = processShareValue(key, value)
                            if (modifiedValue != null) {
                                param.args[1] = modifiedValue
                            }
                        } catch (e: Exception) {
                            XposedBridge.log("$TAG: Error in Intent.putExtra(String, String) hook: ${e.message}")
                        }
                    }
                }
            )

            // Hook putExtra(String, CharSequence) - used by ShareCompat.IntentBuilder
            XposedHelpers.findAndHookMethod(
                intentClass,
                "putExtra",
                String::class.java,
                CharSequence::class.java,
                object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        try {
                            val key = param.args[0] as? String ?: return
                            val value = param.args[1] as? CharSequence ?: return

                            val modifiedValue = processShareValue(key, value.toString())
                            if (modifiedValue != null) {
                                param.args[1] = modifiedValue
                            }
                        } catch (e: Exception) {
                            XposedBridge.log("$TAG: Error in Intent.putExtra(String, CharSequence) hook: ${e.message}")
                        }
                    }
                }
            )

            XposedBridge.log("$TAG: Intent hooks installed successfully")
        } catch (e: Exception) {
            XposedBridge.log("$TAG: Failed to hook Intent: ${e.message}")
        }
    }

    /**
     * Processes a share value, replacing Twitter/X URLs when appropriate.
     * Returns null if no replacement was made.
     */
    private fun processShareValue(key: String, value: String): String? {
        if (key != Intent.EXTRA_TEXT && key != Intent.EXTRA_HTML_TEXT) return null
        if (!TWITTER_URL_PATTERN.containsMatchIn(value)) return null

        val modifiedValue = replaceTwitterUrls(value)
        if (modifiedValue != value) {
            XposedBridge.log("$TAG: Replaced share Intent URL [$key]: $value -> $modifiedValue")
            return modifiedValue
        }
        return null
    }

    /**
     * Replaces Twitter/X URLs with vxtwitter.com format
     * Maintains text surrounding the URL intact
     */
    private fun replaceTwitterUrls(text: String): String {
        return TWITTER_URL_PATTERN.replace(text) { matchResult ->
            val path = matchResult.groupValues[1] // /username/status/123456...
            "$REPLACEMENT_DOMAIN$path"
        }
    }
}
