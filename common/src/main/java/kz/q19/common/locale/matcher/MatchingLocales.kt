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
package kz.q19.common.locale.matcher

import kz.q19.common.locale.LocalePreference
import java.util.*

/**
 * This class represents a pair of matching locales between a supported and a system Locale.
 */
class MatchingLocales constructor(
    val supportedLocale: Locale,
    val systemLocale: Locale
) {

    fun getPreferredLocale(preference: LocalePreference): Locale {
        return if (preference == LocalePreference.PREFER_SUPPORTED_LOCALE) supportedLocale else systemLocale
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is MatchingLocales) return false
        return supportedLocale == other.supportedLocale && systemLocale == other.systemLocale
    }

    override fun hashCode(): Int {
        var result = supportedLocale.hashCode()
        result = 31 * result + systemLocale.hashCode()
        return result
    }

    override fun toString(): String {
        return "$supportedLocale, $systemLocale"
    }

}