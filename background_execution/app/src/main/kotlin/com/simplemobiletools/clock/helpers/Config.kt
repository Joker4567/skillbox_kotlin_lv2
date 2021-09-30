package com.simplemobiletools.clock.helpers

import android.content.Context
import com.simplemobiletools.clock.extensions.gson.gson
import com.simplemobiletools.clock.models.Alarm
import com.simplemobiletools.commons.helpers.BaseConfig

class Config(context: Context) : BaseConfig(context) {
    companion object {
        fun newInstance(context: Context) = Config(context)
    }

    var showSeconds: Boolean
        get() = prefs.getBoolean(SHOW_SECONDS, true)
        set(showSeconds) = prefs.edit().putBoolean(SHOW_SECONDS, showSeconds).apply()

    var selectedTimeZones: Set<String>
        get() = prefs.getStringSet(SELECTED_TIME_ZONES, HashSet())!!
        set(selectedTimeZones) = prefs.edit().putStringSet(SELECTED_TIME_ZONES, selectedTimeZones).apply()

    var editedTimeZoneTitles: Set<String>
        get() = prefs.getStringSet(EDITED_TIME_ZONE_TITLES, HashSet())!!
        set(editedTimeZoneTitles) = prefs.edit().putStringSet(EDITED_TIME_ZONE_TITLES, editedTimeZoneTitles).apply()

    var alarmSort: Int
        get() = prefs.getInt(ALARMS_SORT_BY, SORT_BY_CREATION_ORDER)
        set(alarmSort) = prefs.edit().putInt(ALARMS_SORT_BY, alarmSort).apply()

    var alarmMaxReminderSecs: Int
        get() = prefs.getInt(ALARM_MAX_REMINDER_SECS, DEFAULT_MAX_ALARM_REMINDER_SECS)
        set(alarmMaxReminderSecs) = prefs.edit().putInt(ALARM_MAX_REMINDER_SECS, alarmMaxReminderSecs).apply()

    var useTextShadow: Boolean
        get() = prefs.getBoolean(USE_TEXT_SHADOW, true)
        set(useTextShadow) = prefs.edit().putBoolean(USE_TEXT_SHADOW, useTextShadow).apply()

    var increaseVolumeGradually: Boolean
        get() = prefs.getBoolean(INCREASE_VOLUME_GRADUALLY, true)
        set(increaseVolumeGradually) = prefs.edit().putBoolean(INCREASE_VOLUME_GRADUALLY, increaseVolumeGradually).apply()

    var alarmLastConfig: Alarm?
        get() = prefs.getString(ALARM_LAST_CONFIG, null)?.let { lastAlarm ->
            gson.fromJson(lastAlarm, Alarm::class.java)
        }
        set(alarm) = prefs.edit().putString(ALARM_LAST_CONFIG, gson.toJson(alarm)).apply()
}
