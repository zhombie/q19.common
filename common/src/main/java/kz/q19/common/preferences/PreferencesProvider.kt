package kz.q19.common.preferences

interface PreferencesProvider : AudioRecorderPreferences {
    fun getLanguage(): String
    fun setLanguage(language: String)
}