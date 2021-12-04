package kz.q19.common.sample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import java.util.*

class ChildActivity : BaseActivity() {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ChildActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child)

//        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)

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

        button2.setOnClickListener {
            finish()
        }
    }

}