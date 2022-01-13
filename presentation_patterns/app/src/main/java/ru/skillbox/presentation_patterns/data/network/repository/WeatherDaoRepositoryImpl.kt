package ru.skillbox.presentation_patterns.data.network.repository

import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import javax.inject.Inject

class WeatherDaoRepositoryImpl @Inject constructor(
        private val weatherDao: WeatherDao
) : WeatherDaoRepository {

    override suspend fun getCityLocal(
            city: String
    ) : List<WeatherEntity> =
            weatherDao.getWeatherCity(city)

    override suspend fun deleteArrayCity(
            city: String
    ) {
        weatherDao.getWeatherCity(city).forEach {
            weatherDao.deleteGameAppOfId(it.id)
        }
    }

    override suspend fun getCityListSaveLocal(): List<String> =
            weatherDao.getCityLocal()
}