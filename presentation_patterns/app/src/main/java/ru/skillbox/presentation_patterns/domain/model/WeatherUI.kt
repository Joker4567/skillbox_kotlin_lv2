package ru.skillbox.presentation_patterns.domain.model

data class WeatherUI(
        var id: Long = 0,
        val temperature: String,
        val description: String,
        val city: String,
        val day: String
)
