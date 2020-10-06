package kz.q19.common.preferences

interface PreferencesProvider : AuthPreferences, AudioRecorderPreferences {
    fun getLanguage(): String
    fun setLanguage(language: String)
}