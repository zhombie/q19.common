/*
 * Copyright (c) 2019 Akexorcist
 * GitHub: https://github.com/akexorcist/Localization
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package kz.q19.common.locale.core

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Handler
import android.os.LocaleList
import java.util.*

open class LocalizationActivityDelegate(val activity: Activity) {
    private var isLocalizationChanged = false
    private lateinit var currentLanguage: Locale
    private val localeChangedListeners = ArrayList<OnLocaleChangedListener>()

    companion object {
        private const val KEY_ACTIVITY_LOCALE_CHANGED = "activity_locale_changed"
    }

    fun addOnLocaleChangedListener(onLocaleChangedListener: OnLocaleChangedListener) {
        localeChangedListeners.add(onLocaleChangedListener)
    }

    fun removeOnLocaleChangedListener(onLocaleChangedListener: OnLocaleChangedListener) {
        localeChangedListeners.remove(onLocaleChangedListener)
    }

    fun onCreate() {
        setupLanguage()
        checkBeforeLocaleChanging()
    }

    // If activity is run to back stack. So we have to check if this activity is resume working.
    fun onResume(context: Context) {
        Handler().post {
            checkLocaleChange(context)
            checkAfterLocaleChanging()
        }
    }

    fun attachBaseContext(context: Context): Context {
        val locale = LanguageSetting.getLanguageWithDefault(
            context,
            LanguageSetting.getDefaultLanguage(context)
        )
        val config = context.resources.configuration
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
                context.createConfigurationContext(config)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 -> {
                config.setLocale(locale)
                context.createConfigurationContext(config)
            }
            else -> context
        }
    }

    fun updateConfigurationLocale(context: Context): Configuration {
        val locale = LanguageSetting.getLanguageWithDefault(
            context,
            LanguageSetting.getDefaultLanguage(context)
        )
        val config = context.resources.configuration
        return config.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                config.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                config.setLocales(localeList)
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLocale(locale)
            }
        }
    }

    fun getApplicationContext(applicationContext: Context): Context {
        return LocalizationUtility.applyLocalizationContext(applicationContext)
    }

    fun getResources(resources: Resources): Resources {
        val locale = LanguageSetting.getLanguage(activity)
        val config = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            config.setLocale(locale)
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                config.setLayoutDirection(locale)
            }
            @Suppress("DEPRECATION")
            resources.updateConfiguration(config, resources.displayMetrics)
        }
        return resources
    }

    // Provide method to set application language by country name.
    fun setLanguage(context: Context, newLanguage: String) {
        val locale = Locale(newLanguage)
        setLanguage(context, locale)
    }

    fun setLanguage(context: Context, newLanguage: String, newCountry: String) {
        val locale = Locale(newLanguage, newCountry)
        setLanguage(context, locale)
    }

    fun setLanguage(context: Context, newLocale: Locale) {
        val oldLocale = LanguageSetting.getLanguageWithDefault(
            context,
            LanguageSetting.getDefaultLanguage(context)
        )
        if (!isCurrentLanguageSetting(newLocale, oldLocale)) {
            LanguageSetting.setLanguage(activity, newLocale)
            notifyLanguageChanged()
        }
    }

    // Get current language
    fun getLanguage(context: Context): Locale {
        return LanguageSetting.getLanguageWithDefault(
            context,
            LanguageSetting.getDefaultLanguage(context)
        )
    }

    // Check that bundle come from locale change.
    // If yes, bundle will be remove and set boolean flag to "true".
    private fun checkBeforeLocaleChanging() {
        val isLocalizationChanged =
            activity.intent.getBooleanExtra(KEY_ACTIVITY_LOCALE_CHANGED, false)
        if (isLocalizationChanged) {
            this.isLocalizationChanged = true
            activity.intent.removeExtra(KEY_ACTIVITY_LOCALE_CHANGED)
        }
    }

    // Setup language to locale and language preference.
    // This method will called before onCreate.
    private fun setupLanguage() {
        LanguageSetting.getLanguage(activity)?.let { locale ->
            currentLanguage = locale
        } ?: run {
            checkLocaleChange(activity)
        }
    }

    // Avoid duplicated setup
    private fun isCurrentLanguageSetting(newLocale: Locale, currentLocale: Locale): Boolean {
        return newLocale.toString() == currentLocale.toString()
    }

    // Let's take it change! (Using recreate method that available on API 11 or more.
    private fun notifyLanguageChanged() {
        sendOnBeforeLocaleChangedEvent()
        activity.intent.putExtra(KEY_ACTIVITY_LOCALE_CHANGED, true)
        activity.recreate()
    }

    // Check if locale has change while this activity was run to back stack.
    private fun checkLocaleChange(context: Context) {
        val oldLanguage = LanguageSetting.getLanguageWithDefault(
            context,
            LanguageSetting.getDefaultLanguage(context)
        )
        if (!isCurrentLanguageSetting(currentLanguage, oldLanguage)) {
            isLocalizationChanged = true
            notifyLanguageChanged()
        }
    }

    // Call override method if local is really changed
    private fun checkAfterLocaleChanging() {
        if (isLocalizationChanged) {
            sendOnAfterLocaleChangedEvent()
            isLocalizationChanged = false
        }
    }

    private fun sendOnBeforeLocaleChangedEvent() {
        for (changedListener in localeChangedListeners) {
            changedListener.onBeforeLocaleChanged()
        }
    }

    private fun sendOnAfterLocaleChangedEvent() {
        for (listener in localeChangedListeners) {
            listener.onAfterLocaleChanged()
        }
    }
}