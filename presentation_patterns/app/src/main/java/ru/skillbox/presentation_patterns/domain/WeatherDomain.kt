package ru.skillbox.presentation_patterns.domain

import ru.skillbox.presentation_patterns.domain.model.WeatherUI
import ru.skillbox.presentation_patterns.utils.platform.State

interface WeatherDomain {
    suspend fun getWeatherCity(
            city: String,
            onState: (State) -> Unit
    ) : List<WeatherUI>
    suspend fun getCityLocal(
            city: String
    ) : List<WeatherUI>
    suspend fun deleteArrayCity(
            city: String
    )
    suspend fun getCityListSaveLocal() : List<String>
}