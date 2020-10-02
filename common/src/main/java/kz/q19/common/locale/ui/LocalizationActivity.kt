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

package kz.q19.common.locale.ui

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import kz.q19.common.locale.core.LocalizationActivityDelegate
import kz.q19.common.locale.core.OnLocaleChangedListener
import java.util.*

abstract class LocalizationActivity : AppCompatActivity, OnLocaleChangedListener {

    constructor() : super()
    constructor(@LayoutRes contentLayoutId: Int) : super(contentLayoutId)

    private val localizationDelegate: LocalizationActivityDelegate by lazy {
        LocalizationActivityDelegate(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        localizationDelegate.addOnLocaleChangedListener(this)
        localizationDelegate.onCreate()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        localizationDelegate.onResume(this)
    }

    override fun attachBaseContext(newBase: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            applyOverrideConfiguration(localizationDelegate.updateConfigurationLocale(newBase))
            super.attachBaseContext(newBase)
        } else {
            super.attachBaseContext(localizationDelegate.attachBaseContext(newBase))
        }
    }

    override fun getBaseContext(): Context {
        return localizationDelegate.getApplicationContext(super.getBaseContext())
    }

    override fun getApplicationContext(): Context {
        return localizationDelegate.getApplicationContext(super.getApplicationContext())
    }

    override fun getResources(): Resources {
        return localizationDelegate.getResources(super.getResources())
    }

    override fun onBeforeLocaleChanged() {
        // Just override method locale change event
    }

    override fun onAfterLocaleChanged() {
        // Just override method locale change event
    }

    fun setLanguage(language: String) {
        localizationDelegate.setLanguage(this, language)
    }

    fun setLanguage(language: String, country: String) {
        localizationDelegate.setLanguage(this, language, country)
    }

    fun setLocale(locale: Locale) {
        localizationDelegate.setLanguage(this, locale)
    }

    fun getCurrentLocale(): Locale {
        return localizationDelegate.getLanguage(this)
    }

}