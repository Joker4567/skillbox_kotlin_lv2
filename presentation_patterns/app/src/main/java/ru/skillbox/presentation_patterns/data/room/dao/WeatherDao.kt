package ru.skillbox.presentation_patterns.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherCity: WeatherEntity)

    @Query("SELECT * FROM WeatherEntity WHERE city = :city")
    suspend fun getWeatherCity(city: String) : List<WeatherEntity>

    @Query("DELETE FROM WeatherEntity WHERE id = :id")
    suspend fun deleteGameAppOfId(id: Long)

    @Query("SELECT DISTINCT city FROM WeatherEntity")
    suspend fun getCityLocal() : List<String>
}