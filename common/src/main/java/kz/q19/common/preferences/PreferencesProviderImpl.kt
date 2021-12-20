package kz.q19.common.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesProviderImpl private constructor(context: Context) : PreferencesProvider {

    companion object {
        private const val PREFERENCES_NAME = "q19.preferences"

        @Volatile
        private var INSTANCE: PreferencesProviderImpl? = null

        fun getInstance(context: Context): PreferencesProviderImpl =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesProviderImpl(context).also { INSTANCE = it }
            }
    }

    private object Key {
        const val LANGUAGE = "language"
        const val USER_ID = "user_id"
        const val TOKEN = "token"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    /**
     * [PreferencesProvider] implementation
     */

    override fun getLanguage(): String {
        return sharedPreferences.getString(Key.LANGUAGE, "") ?: ""
    }

    override fun setLanguage(language: String) {
        apply { putString(Key.LANGUAGE, language) }
    }

    /**
     * [AuthPreferences] implementation
     */

    override fun getUserInfo(): Pair<Long, String>? {
        val userId = sharedPreferences.getLong(Key.USER_ID, -1L)
        val token = sharedPreferences.getString(Key.TOKEN, null)

        return if (userId > -1L && !token.isNullOrBlank()) {
            userId to token
        } else {
            null
        }
    }

    override fun setUserInfo(userInfo: Pair<Long, String>) {
        apply {
            putLong(Key.USER_ID, userInfo.first)
            putString(Key.TOKEN, userInfo.second)
        }
    }

    override fun removeUserInfo() {
        apply {
            remove(Key.USER_ID)
            remove(Key.TOKEN)
        }
    }

    private fun apply(lambda: SharedPreferences.Editor.() -> Unit): SharedPreferences.Editor? {
        val editor = sharedPreferences.edit()
        editor.apply(lambda)
        editor.apply()
        return editor
    }

}