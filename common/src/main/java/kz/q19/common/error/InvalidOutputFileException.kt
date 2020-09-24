@file:Suppress("unused")

import kz.q19.common.R
import kz.q19.common.error.BaseException

class InvalidOutputFileException : BaseException() {
    override val text: Int
        get() = R.string.error_invalid_output_file
}