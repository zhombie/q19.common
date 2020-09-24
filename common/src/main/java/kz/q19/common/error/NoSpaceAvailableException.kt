@file:Suppress("unused")

package kz.q19.common.error

import kz.q19.common.R

class NoSpaceAvailableException : BaseException() {
    override val text: Int
        get() = R.string.error_no_available_space
}