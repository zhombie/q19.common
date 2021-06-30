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

import android.content.Context
import android.content.SharedPreferences
import java.util.*

/**
 * This class provides persistence
 */
internal class LocalePersistor constructor(context: Context) {

    companion object {
        private const val NAME_SHARED_PREFERENCES = "LocaleManager.LocalePersistence"

        private const val KEY_LANGUAGE = "language"
        private const val KEY_COUNTRY = "country"
        private const val KEY_VARIANT = "variant"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun load(): Locale? {
        var locale: Locale? = null
        val language = sharedPreferences.getString(KEY_LANGUAGE, "")
        if (!language.isNullOrEmpty()) {
            locale = Locale(
                language,
                sharedPreferences.getString(KEY_COUNTRY, "") ?: "",
                sharedPreferences.getString(KEY_VARIANT, "") ?: ""
            )
        }
        return locale
    }

    fun save(locale: Locale) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_LANGUAGE, locale.language)
        editor.putString(KEY_COUNTRY, locale.country)
        editor.putString(KEY_VARIANT, locale.variant)
        editor.apply()
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

}