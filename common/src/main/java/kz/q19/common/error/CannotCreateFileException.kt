@file:Suppress("unused")

package kz.q19.common.error

import kz.q19.common.R

class CannotCreateFileException : BaseException() {
    override val text: Int
        get() = R.string.error_cannot_create_file
}