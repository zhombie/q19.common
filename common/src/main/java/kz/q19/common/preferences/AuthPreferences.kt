package kz.q19.common.preferences

import kz.q19.common.model.UserInfo

interface AuthPreferences {
    fun getUserInfo(): UserInfo?
    fun setUserInfo(userInfo: UserInfo)
}