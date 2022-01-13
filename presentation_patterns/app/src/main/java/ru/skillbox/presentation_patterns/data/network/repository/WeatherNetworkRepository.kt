package ru.skillbox.presentation_patterns.data.network.repository

import ru.skillbox.presentation_patterns.domain.model.WeatherUI
import ru.skillbox.presentation_patterns.utils.platform.State

interface WeatherNetworkRepository {
    suspend fun getWeatherCity(
            city: String,
            onSuccess: (List<WeatherUI>) -> Unit,
            onState: (State) -> Unit
    )
}