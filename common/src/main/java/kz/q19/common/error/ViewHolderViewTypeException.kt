@file:Suppress("unused")

package kz.q19.common.error

class ViewHolderViewTypeException(private val viewType: Int) : RuntimeException() {

    override val message: String?
        get() = "There is no ViewHolder for viewType: $viewType"

}