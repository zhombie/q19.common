package kz.q19.common

import kz.q19.common.locale.ui.LocalizationApplication
import java.util.*

class Application : LocalizationApplication() {

    override fun getDefaultLocale(): Locale = Locale.getDefault()

}