package ru.skillbox.presentation_patterns.data.network.repository

import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity

interface WeatherDaoRepository {
    suspend fun getCityLocal(
            city: String
    ) : List<WeatherEntity>
    suspend fun deleteArrayCity(
            city: String
    )
    suspend fun getCityListSaveLocal() : List<String>
}