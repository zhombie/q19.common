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

import kz.q19.common.locale.utils.LocaleMatcher
import java.util.*

/**
 * An algorithm that matches the Locales with most attributes in common.
 */
class ClosestMatchingAlgorithm : MatchingAlgorithm {

    override fun findDefaultMatch(
        supportedLocales: List<Locale>,
        systemLocales: List<Locale>
    ): MatchingLocales? {
        var bestMatchingLocalePair: MatchingLocales? = null
        var languageAndCountryMatchingLocalePair: MatchingLocales? = null
        var languageMatchingLocalePair: MatchingLocales? = null
        for (systemLocale in systemLocales) {
            for (supportedLocale in supportedLocales) {
                val match = LocaleMatcher.match(systemLocale, supportedLocale)
                if (match == LocaleMatcher.MatchLevel.COMPLETE_MATCH) {
                    bestMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                    break
                } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_AND_COUNTRY_MATCH && languageAndCountryMatchingLocalePair == null) {
                    languageAndCountryMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_MATCH && languageMatchingLocalePair == null) {
                    languageMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                }
            }
            if (bestMatchingLocalePair != null) break
        }
        return bestMatchingLocalePair ?: (languageAndCountryMatchingLocalePair ?: languageMatchingLocalePair)
    }

    override fun findMatch(supportedLocale: Locale, systemLocales: List<Locale>): MatchingLocales? {
        var bestMatchingLocalePair: MatchingLocales? = null
        var languageAndCountryMatchingLocalePair: MatchingLocales? = null
        var languageMatchingLocalePair: MatchingLocales? = null
        for (systemLocale in systemLocales) {
            val match = LocaleMatcher.match(systemLocale, supportedLocale)
            if (match == LocaleMatcher.MatchLevel.COMPLETE_MATCH) {
                bestMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
                break
            } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_AND_COUNTRY_MATCH && languageAndCountryMatchingLocalePair == null) {
                languageAndCountryMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
            } else if (match == LocaleMatcher.MatchLevel.LANGUAGE_MATCH && languageMatchingLocalePair == null) {
                languageMatchingLocalePair = MatchingLocales(supportedLocale, systemLocale)
            }
        }
        return bestMatchingLocalePair ?: (languageAndCountryMatchingLocalePair ?: languageMatchingLocalePair)
    }
}