package ru.skillbox.presentation_patterns.data.network.repository

import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.utils.platform.State

interface WeatherRepository {
    suspend fun getWeatherCity(
            city: String,
            onSuccess: (List<WeatherEntity>) -> Unit,
            onState: (State) -> Unit
    )
    suspend fun getCityLocal(
            city: String
    ) : List<WeatherEntity>
    suspend fun deleteArrayCity(
            city: String
    )
    suspend fun getCityListSaveLocal() : List<String>
}