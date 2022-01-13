package ru.skillbox.presentation_patterns.data.network.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.skillbox.presentation_patterns.data.network.api.WeatherApi
import ru.skillbox.presentation_patterns.data.network.model.WeatherScheme
import ru.skillbox.presentation_patterns.data.room.dao.WeatherDao
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.domain.mapper.mapToWeatherUI
import ru.skillbox.presentation_patterns.domain.model.WeatherUI
import ru.skillbox.presentation_patterns.utils.extension.CallbackC
import ru.skillbox.presentation_patterns.utils.extension.getDateCurrent
import ru.skillbox.presentation_patterns.utils.network.ErrorHandler
import ru.skillbox.presentation_patterns.utils.network.Failure
import ru.skillbox.presentation_patterns.utils.platform.BaseRepository
import ru.skillbox.presentation_patterns.utils.platform.State
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class WeatherNetworkRepositoryImpl @Inject constructor(
        private val api: WeatherApi,
        private val weatherDao: WeatherDao
) : WeatherNetworkRepository {

    override suspend fun getWeatherCity(
            city: String,
            onState: (State) -> Unit
    ): List<WeatherUI> = suspendCancellableCoroutine { cancellableContinuation ->
        onState.invoke(State.Loading)
        fetchWeatherFromNetwork(city, object : CallbackC<WeatherScheme> {
            override fun onSuccess(model: WeatherScheme) {
                CoroutineScope(Dispatchers.Main).launch {
                    weatherDao.insertWeather(
                            WeatherEntity(
                                    id = 0,
                                    temperature = model.temperature,
                                    description = model.description,
                                    city,
                                    getDateCurrent()
                            )
                    )
                    val list = weatherDao.getWeatherCity(city).map { it.mapToWeatherUI() }
                    cancellableContinuation.resume(list)
                    cancellableContinuation.cancel()
                }
            }

            override fun onFailure(exception: Throwable) {
                onState.invoke(State.Error(Failure.ServerError))
                cancellableContinuation.resumeWithException(exception)
                cancellableContinuation.cancel()
            }
        })
    }


    private fun fetchWeatherFromNetwork(city: String, callback: CallbackC<WeatherScheme>) {
        CoroutineScope(Dispatchers.IO).launch {
            api.getCityWeather(city).enqueue(object : Callback<WeatherScheme> {
                override fun onResponse(call: Call<WeatherScheme>, response: Response<WeatherScheme>) {
                    if (response.isSuccessful) {
                        callback.onSuccess(response.body() as WeatherScheme)
                    }
                }

                override fun onFailure(call: Call<WeatherScheme>, t: Throwable) {
                    callback.onFailure(t)
                }
            })
        }
    }
}