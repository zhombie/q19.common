package kz.q19.common.preferences

interface PreferencesProvider : AuthPreferences, AudioRecordPreferences {
    fun getLanguage(): String
    fun setLanguage(language: String)
}