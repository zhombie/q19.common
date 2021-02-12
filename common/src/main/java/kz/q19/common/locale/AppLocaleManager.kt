/*
 * Copyright (c)  2017  Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package kz.q19.common.locale

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

internal class AppLocaleManager constructor(private val context: Context) {

    fun change(newLocale: Locale) {
        Locale.setDefault(newLocale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateConfiguration(newLocale)
        } else {
            updateConfigurationLegacy(newLocale)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun updateConfiguration(newLocale: Locale) {
        context.resources.configuration.setLocale(newLocale)
    }

    @Suppress("DEPRECATION")
    private fun updateConfigurationLegacy(newLocale: Locale) {
        val configuration = context.resources.configuration
        configuration.locale = newLocale
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }

    @SuppressLint("ObsoleteSdkInt")
    fun configureBaseContext(context: Context, locale: Locale): Context {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            getLocaleConfiguredContext(context, locale)
        } else {
            context
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun getLocaleConfiguredContext(context: Context, locale: Locale): Context {
        val conf = context.resources.configuration
        conf.setLocale(locale)
        return context.createConfigurationContext(conf)
    }

}