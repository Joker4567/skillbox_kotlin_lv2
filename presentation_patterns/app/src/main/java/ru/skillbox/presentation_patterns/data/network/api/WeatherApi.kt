package ru.skillbox.presentation_patterns.data.network.api

import retrofit2.Call
import retrofit2.http.*
import ru.skillbox.presentation_patterns.data.network.model.WeatherScheme

interface WeatherApi {
    @GET("weather/{city}")
    fun getCityWeather(@Path("city") city: String): Call<WeatherScheme>
}