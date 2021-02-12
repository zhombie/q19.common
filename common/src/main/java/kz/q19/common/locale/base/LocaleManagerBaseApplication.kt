package kz.q19.common.locale.base

import android.app.Application
import android.content.res.Configuration
import kz.q19.common.locale.LocaleManager

/**
 * Base [Application] class to inherit from with all needed configuration.
 */
abstract class LocaleManagerBaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeLocaleManager()
    }

    /**
     * Call [LocaleManager.initialize] in here
     */
    abstract fun initializeLocaleManager()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.onConfigurationChanged()
    }

}