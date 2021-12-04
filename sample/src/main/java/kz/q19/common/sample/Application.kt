package kz.q19.common.sample

import android.app.Application
import android.content.res.Configuration
import kz.q19.common.locale.LocaleManager
import java.util.*

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        val supportedLocales = listOf(Locale.ENGLISH, Locale("ru"), Locale("kk"))
        LocaleManager.initialize(applicationContext, supportedLocales)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.onConfigurationChanged()
    }

}