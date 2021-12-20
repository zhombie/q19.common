package kz.q19.common.preferences

interface PreferencesProvider : AuthPreferences {
    fun getLanguage(): String
    fun setLanguage(language: String)
}