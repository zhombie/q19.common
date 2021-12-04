package kz.q19.common.sample

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.*
import java.util.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            startActivity(ChildActivity.newIntent(this))
        }
    }

}