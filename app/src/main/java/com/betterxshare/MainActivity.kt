package com.betterxshare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import android.widget.TextView
import android.widget.Switch
import android.widget.LinearLayout
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Simple module info UI - built programmatically (no layout XML needed)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 48, 48, 48)
            gravity = Gravity.CENTER
        }

        val titleText = TextView(this).apply {
            text = "BetterXShare"
            textSize = 28f
            setTextColor(resources.getColor(R.color.purple_500, theme))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 32
            }
        }

        val descriptionText = TextView(this).apply {
            text = "Módulo LSPosed para reemplazar enlaces de Twitter/X\n" +
                    "\n" +
                    "Este módulo modifica automáticamente:\n" +
                    "• Acción de copiar enlace\n" +
                    "• Menú de compartir\n" +
                    "\n" +
                    "Reemplaza twitter.com/x.com → vxtwitter.com"
            textSize = 16f
            setTextColor(resources.getColor(R.color.purple_700, theme))
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 48
            }
        }

        val statusCard = MaterialCardView(this).apply {
            radius = 16f
            cardElevation = 4f
            setCardBackgroundColor(resources.getColor(R.color.purple_100, theme))
            layoutParams = LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 32
            }
        }

        val cardContent = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val statusText = TextView(this).apply {
            text = "Módulo instalado correctamente\n" +
                    "Actívelo en la configuración de LSPosed"
            textSize = 14f
            setTextColor(resources.getColor(R.color.purple_700, theme))
            gravity = Gravity.CENTER
        }

        cardContent.addView(statusText)
        statusCard.addView(cardContent)

        layout.addView(titleText)
        layout.addView(descriptionText)
        layout.addView(statusCard)

        setContentView(layout)
    }
}
