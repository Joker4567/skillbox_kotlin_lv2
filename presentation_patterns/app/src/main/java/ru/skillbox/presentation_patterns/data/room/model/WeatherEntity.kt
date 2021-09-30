package ru.skillbox.presentation_patterns.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        val temperature: String,
        val description: String,
        val city: String,
        val day: String
)
