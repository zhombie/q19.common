package kz.q19.common.error

import androidx.annotation.StringRes

abstract class BaseException : Exception() {
    abstract val text: Int
        @StringRes get
}