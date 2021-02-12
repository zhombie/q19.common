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
package kz.q19.common.locale.utils

import android.os.Build
import android.os.LocaleList
import androidx.annotation.RequiresApi
import java.util.*

/**
 * A class useful to retrieve the system configured Locales.
 */
object SystemLocaleRetriever {

    @JvmStatic
    fun retrieve(): List<Locale> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mapToListOfLocales(LocaleList.getDefault())
        } else {
            listOf(Locale.getDefault())
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private fun mapToListOfLocales(localeList: LocaleList): List<Locale> {
        val locales = ArrayList<Locale>()
        for (i in 0 until localeList.size()) {
            locales.add(localeList[i])
        }
        return locales
    }

}