package kz.q19.common.preferences

import android.content.Context
import android.content.SharedPreferences

class PreferencesProviderImpl private constructor(context: Context) : PreferencesProvider {

    companion object {
        private const val PREFERENCES_NAME = "q19.preferences"

        private const val KEY_LANGUAGE = "language"
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

    override fun getLanguage(): String {
        return sharedPreferences.getString(KEY_LANGUAGE, "") ?: ""
    }

    override fun setLanguage(language: String) {
        apply { putString(KEY_LANGUAGE, language) }
    }

    override fun getActiveAudioRecordId(): Long {
        return sharedPreferences.getLong(KEY_ACTIVE_AUDIO_RECORD_ID, -1)
    }

    override fun setActiveAudioRecordId(id: Long) {
        apply { putLong(KEY_ACTIVE_AUDIO_RECORD_ID, id) }
    }

    private fun apply(lambda: SharedPreferences.Editor.() -> Unit): SharedPreferences.Editor? {
        val editor = sharedPreferences.edit()
        editor.apply(lambda)
        editor.apply()
        return editor
    }

}