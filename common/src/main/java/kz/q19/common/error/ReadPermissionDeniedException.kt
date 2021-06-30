package kz.q19.common.error

import kz.q19.common.R

class ReadPermissionDeniedException : BaseException() {
    override val text: Int
        get() = R.string.error_read_permission_denied
}