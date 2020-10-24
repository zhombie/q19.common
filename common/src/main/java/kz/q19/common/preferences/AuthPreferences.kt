package kz.q19.common.preferences

interface AuthPreferences {
    fun getUserInfo(): Pair<Long, String>?
    fun setUserInfo(userInfo: Pair<Long, String>)
    fun removeUserInfo()
}