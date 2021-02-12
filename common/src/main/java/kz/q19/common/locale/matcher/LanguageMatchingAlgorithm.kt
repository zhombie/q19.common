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
 * An algorithm that match the Locales the same language in common.
 */
class LanguageMatchingAlgorithm : MatchingAlgorithm {

    companion object {
        private fun findMatchingLocale(localeToMatch: Locale, candidates: List<Locale>): Locale? {
            var matchingLocale: Locale? = null
            for (candidate in candidates) {
                val matchLevel = LocaleMatcher.match(localeToMatch, candidate)
                if (matchLevel != LocaleMatcher.MatchLevel.NO_MATCH) {
                    matchingLocale = candidate
                    break
                }
            }
            return matchingLocale
        }
    }

    override fun findDefaultMatch(
        supportedLocales: List<Locale>,
        systemLocales: List<Locale>
    ): MatchingLocales? {
        var matchingPair: MatchingLocales? = null
        for (systemLocale in systemLocales) {
            val matchingSupportedLocale = findMatchingLocale(systemLocale, supportedLocales)
            if (matchingSupportedLocale != null) {
                matchingPair = MatchingLocales(matchingSupportedLocale, systemLocale)
                break
            }
        }
        return matchingPair
    }

    override fun findMatch(supportedLocale: Locale, systemLocales: List<Locale>): MatchingLocales? {
        val matchingSystemLocale = findMatchingLocale(supportedLocale, systemLocales)
        return if (matchingSystemLocale != null) {
            MatchingLocales(supportedLocale, matchingSystemLocale)
        } else null
    }

}