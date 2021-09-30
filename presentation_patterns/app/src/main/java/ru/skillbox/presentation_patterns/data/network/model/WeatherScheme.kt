package ru.skillbox.presentation_patterns.data.network.model

import com.google.gson.annotations.SerializedName

data class WeatherScheme(
        @SerializedName("temperature")
        val temperature: String,
        @SerializedName("description")
        val description: String
)