package kz.q19.common.locale.base

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.LocaleManagerAppCompatDelegate
import kz.q19.common.locale.utils.ActivityRecreationHelper

/**
 * Base [android.app.Activity] class to inherit from with all needed configuration.
 */
class LocaleManagerBaseActivity : AppCompatActivity() {

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
        ActivityRecreationHelper.onDestroy(this)
        super.onDestroy()
    }

}