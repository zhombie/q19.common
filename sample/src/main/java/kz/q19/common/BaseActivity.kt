package kz.q19.common

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.LocaleManagerAppCompatDelegate
import kz.q19.common.locale.LocaleManager
import kz.q19.common.locale.utils.ActivityRecreationHelper
import java.util.*

open class BaseActivity : AppCompatActivity() {

    private var localeManagerAppCompatDelegate: LocaleManagerAppCompatDelegate? = null

    override fun getDelegate(): AppCompatDelegate {
        if (localeManagerAppCompatDelegate == null) {
            localeManagerAppCompatDelegate = LocaleManagerAppCompatDelegate(super.getDelegate())
        }
        return requireNotNull(localeManagerAppCompatDelegate)
    }

    override fun onResume() {
        super.onResume()
        ActivityRecreationHelper.onResume(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityRecreationHelper.onDestroy(this)
    }

    fun setLocale(locale: Locale) {
        LocaleManager.setLocale(locale)
        ActivityRecreationHelper.recreate(this, true)
    }

}