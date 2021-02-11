package kz.q19.common.preferences

interface AudioRecordPreferences {
    fun getActiveAudioRecordId(): Long
    fun setActiveAudioRecordId(id: Long)
    fun removeActiveAudioRecordId()
}