package kz.q19.common.preferences

interface AudioRecorderPreferences {
    fun getActiveAudioRecordId(): Long
    fun setActiveAudioRecordId(id: Long)
    fun removeActiveAudioRecordId()
}