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

import kz.q19.common.locale.matcher.MatchingAlgorithm
import kz.q19.common.locale.matcher.MatchingLocales
import java.util.*

/**
 * Class that uses a [MatchingAlgorithm] and a [LocalePreference] to resolve a Locale to be set.
 */
internal class LocaleResolver constructor(
    private val supportedLocales: List<Locale>,
    private val systemLocales: List<Locale>,
    private val matchingAlgorithm: MatchingAlgorithm,
    private val preference: LocalePreference
) {

    fun resolveDefault(): DefaultResolvedLocalePair {
        val matchingPair = matchingAlgorithm.findDefaultMatch(supportedLocales, systemLocales)
        return if (matchingPair != null) {
            DefaultResolvedLocalePair(matchingPair.supportedLocale, matchingPair.getPreferredLocale(preference))
        } else {
            DefaultResolvedLocalePair(supportedLocales[0], supportedLocales[0])
        }
    }

    @Throws(UnsupportedLocaleException::class)
    fun resolve(supportedLocale: Locale): Locale {
        if (!supportedLocales.contains(supportedLocale)) {
            throw UnsupportedLocaleException(message = "The Locale you are trying to load is not in the supported list provided on library initialization")
        }
        var matchingPair: MatchingLocales? = null
        if (preference == LocalePreference.PREFER_SYSTEM_LOCALE) {
            matchingPair = matchingAlgorithm.findMatch(supportedLocale, systemLocales)
        }
        return matchingPair?.getPreferredLocale(preference) ?: supportedLocale
    }

}