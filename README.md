# BetterXShare

---

## English

An [LSPosed](https://github.com/LSPosed/LSPosed) module for Android that automatically replaces Twitter/X links (`twitter.com` / `x.com`) with `vxtwitter.com` when copying or sharing from the official X app.

### What it does

- **Copy link**: when you copy a post link in the X app, the module intercepts the clipboard and changes the domain.
- **Share**: when you use the share button, the module modifies the outgoing `Intent` before it reaches other apps (WhatsApp, Telegram, etc.).

### Example

```text
https://x.com/i/status/2079124440336458101
        ↓
https://vxtwitter.com/i/status/2079124440336458101
```

### Requirements

- Android 8.0+ (API 26).
- [LSPosed](https://github.com/LSPosed/LSPosed) installed and active.
- Official X app (`com.twitter.android`) installed.

### Installation

1. Download the latest APK from [Releases](https://github.com/mathygamersYT/BetterXShare/releases).
2. Install it like any other app.
3. Open LSPosed → Modules → enable **betterXshare**.
4. Make sure the module scope includes `com.twitter.android`.
5. Restart the phone (or force stop the X app).

### Build from source

```bash
./gradlew assembleDebug
```

The generated APK is located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### Project structure

```text
app/src/main/java/com/betterxshare/hooks/MainHook.kt   # Main hook logic
app/src/main/AndroidManifest.xml                        # LSPosed module metadata
app/src/main/assets/xposed_init                         # Module entry point
app/src/main/res/values/arrays.xml                     # Scope: com.twitter.android
libs/api-82.jar                                         # Xposed API (compile-only)
```

### How it works

The module uses two hooks in the X app process:

1. `ClipboardManager.setPrimaryClip(ClipData)`: intercepts copied text.
2. `Intent.putExtra(String, String)` and `Intent.putExtra(String, CharSequence)`: intercept shared text (the X app uses `CharSequence` via `ShareCompat`).

When the text contains a `twitter.com` or `x.com` URL, the domain is replaced with `vxtwitter.com`, keeping the rest of the text intact.

### Notes

- Only works with the official X app (`com.twitter.android`).
- The Xposed API is not included in the APK; it is used as `compileOnly`.
- The module has no functionality without LSPosed.

### License

MIT

### Credits

- Developed by [mathygamersYT](https://github.com/mathygamersYT).
- Based on the Xposed/LSPosed API.
- Inspired by [BetterTwitFix](https://github.com/dylanpdx/BetterTwitFix).

---

## Español

Módulo [LSPosed](https://github.com/LSPosed/LSPosed) para Android que reemplaza automáticamente los enlaces de Twitter/X (`twitter.com` / `x.com`) por `vxtwitter.com` al copiar o compartir desde la app oficial de X.

### Qué hace

- **Copiar enlace**: cuando copias el enlace de un post en la app de X, el módulo intercepta el portapapeles y cambia el dominio.
- **Compartir**: cuando usas el botón de compartir, el módulo modifica el `Intent` de envío antes de que llegue a otras apps (WhatsApp, Telegram, etc.).

### Ejemplo

```text
https://x.com/i/status/2079124440336458101
        ↓
https://vxtwitter.com/i/status/2079124440336458101
```

### Requisitos

- Android 8.0+ (API 26).
- [LSPosed](https://github.com/LSPosed/LSPosed) instalado y activo.
- App oficial de X (`com.twitter.android`) instalada.

### Instalación

1. Descarga el último APK desde [Releases](https://github.com/mathygamersYT/BetterXShare/releases).
2. Instálalo como cualquier otra app.
3. Abre LSPosed → Módulos → activa **betterXshare**.
4. Asegúrate de que el alcance del módulo incluya `com.twitter.android`.
5. Reinicia el teléfono (o fuerza la detención de la app de X).

### Compilar desde el código fuente

```bash
./gradlew assembleDebug
```

El APK generado se encuentra en:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### Estructura del proyecto

```text
app/src/main/java/com/betterxshare/hooks/MainHook.kt   # Lógica principal de los hooks
app/src/main/AndroidManifest.xml                        # Metadatos del módulo LSPosed
app/src/main/assets/xposed_init                         # Clase de entrada del módulo
app/src/main/res/values/arrays.xml                     # Alcance: com.twitter.android
libs/api-82.jar                                         # API de Xposed (compile-only)
```

### Cómo funciona

El módulo usa dos hooks en el proceso de la app de X:

1. `ClipboardManager.setPrimaryClip(ClipData)`: intercepta el texto copiado.
2. `Intent.putExtra(String, String)` y `Intent.putExtra(String, CharSequence)`: interceptan el texto compartido (la app de X usa `CharSequence` a través de `ShareCompat`).

Cuando el texto contiene una URL de `twitter.com` o `x.com`, se reemplaza el dominio por `vxtwitter.com` manteniendo el resto del texto intacto.

### Notas

- Solo funciona con la app oficial de X (`com.twitter.android`).
- No se incluye la API de Xposed en el APK; se usa como `compileOnly`.
- El módulo no tiene funcionalidad sin LSPosed.

### Licencia

MIT

### Créditos

- Desarrollado por [mathygamersYT](https://github.com/mathygamersYT).
- Basado en la API de Xposed/LSPosed.
- Inspirado en [BetterTwitFix](https://github.com/dylanpdx/BetterTwitFix).
