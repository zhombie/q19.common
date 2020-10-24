@file:Suppress("unused")

package kz.q19.common.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesProviderImpl private constructor(context: Context) : PreferencesProvider {

    companion object {
        private const val PREFERENCES_NAME = "q19.preferences"

        private const val KEY_LANGUAGE = "language"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_TOKEN = "token"
        private const val KEY_ACTIVE_AUDIO_RECORD_ID = "active_audio_record_id"

        @Volatile
        private var INSTANCE: PreferencesProviderImpl? = null

        fun getInstance(context: Context): PreferencesProviderImpl =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: PreferencesProviderImpl(context).also { INSTANCE = it }
            }
    }

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    /**
     * [PreferencesProvider] implementation
     */

    override fun getLanguage(): String {
        return sharedPreferences.getString(KEY_LANGUAGE, "") ?: ""
    }

    override fun setLanguage(language: String) {
        apply { putString(KEY_LANGUAGE, language) }
    }

    /**
     * [AuthPreferences] implementation
     */

    override fun getUserInfo(): Pair<Long, String>? {
        val userId = sharedPreferences.getLong(KEY_USER_ID, -1L)
        val token = sharedPreferences.getString(KEY_TOKEN, null)

        return if (userId > -1L && !token.isNullOrBlank()) {
            userId to token
        } else {
            null
        }
    }

    override fun setUserInfo(userInfo: Pair<Long, String>) {
        apply {
            putLong(KEY_USER_ID, userInfo.first)
            putString(KEY_TOKEN, userInfo.second)
        }
    }

    override fun removeUserInfo() {
        apply {
            remove(KEY_USER_ID)
            remove(KEY_TOKEN)
        }
    }

    /**
     * [AudioRecorderPreferences] implementation
     */

    override fun getActiveAudioRecordId(): Long {
        return sharedPreferences.getLong(KEY_ACTIVE_AUDIO_RECORD_ID, -1)
    }

    override fun setActiveAudioRecordId(id: Long) {
        apply { putLong(KEY_ACTIVE_AUDIO_RECORD_ID, id) }
    }

    override fun removeActiveAudioRecordId() {
        apply { remove(KEY_ACTIVE_AUDIO_RECORD_ID) }
    }

    private fun apply(lambda: SharedPreferences.Editor.() -> Unit): SharedPreferences.Editor? {
        val editor = sharedPreferences.edit()
        editor.apply(lambda)
        editor.apply()
        return editor
    }

}