@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package kz.q19.common.error

class SomethingWentWrong(val text: String? = null) : RuntimeException() {

    override val message: String?
        get() = if (text.isNullOrBlank()) super.message else "${text}. ${super.message}"

}