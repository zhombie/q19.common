package kz.q19.common.error

class ViewHolderViewTypeException constructor(private val viewType: Int) : RuntimeException() {

    override val message: String
        get() = "There is no ViewHolder for viewType: $viewType"

}