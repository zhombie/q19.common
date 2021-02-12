package kz.q19.common

import android.app.Application
import android.content.res.Configuration
import kz.q19.common.locale.LocaleManager
import java.util.*

class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        LocaleManager.initialize(applicationContext, listOf(Locale.ENGLISH, Locale("ru"), Locale("kk")))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.onConfigurationChanged()
    }

}