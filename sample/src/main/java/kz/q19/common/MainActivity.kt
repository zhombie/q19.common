package kz.q19.common

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import kz.q19.common.locale.ui.LocalizationActivity
import java.util.*

class MainActivity : LocalizationActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {
            AlertDialog.Builder(this)
                .setItems(arrayOf("Kaz", "Rus")) { dialog, which ->
                    dialog.dismiss()

                    val locale = when (which) {
                        0 -> "kk"
                        1 -> "ru"
                        else -> return@setItems
                    }

                    setLocale(Locale(locale))
                }
                .show()
        }
    }

}