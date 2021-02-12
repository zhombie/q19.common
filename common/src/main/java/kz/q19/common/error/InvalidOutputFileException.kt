package kz.q19.common.error

import kz.q19.common.R

class InvalidOutputFileException : BaseException() {
    override val text: Int
        get() = R.string.error_invalid_output_file
}