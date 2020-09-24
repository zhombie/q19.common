@file:Suppress("unused")

import kz.q19.common.R
import kz.q19.common.error.BaseException

class ReadPermissionDeniedException : BaseException() {
    override val text: Int
        get() = R.string.error_read_permission_denied
}