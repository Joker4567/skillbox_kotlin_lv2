package ru.skillbox.presentation_patterns.domain

import ru.skillbox.presentation_patterns.data.network.repository.WeatherDaoRepository
import ru.skillbox.presentation_patterns.data.network.repository.WeatherNetworkRepository
import ru.skillbox.presentation_patterns.domain.mapper.mapToWeatherUI
import ru.skillbox.presentation_patterns.domain.model.WeatherUI
import ru.skillbox.presentation_patterns.utils.platform.State
import javax.inject.Inject

class WeatherDomainImpl @Inject constructor(
        private val network: WeatherNetworkRepository,
        private val local: WeatherDaoRepository
) : WeatherDomain {
    override suspend fun getWeatherCity(
            city: String,
            onSuccess: (List<WeatherUI>) -> Unit,
            onState: (State) -> Unit
    ) = network.getWeatherCity(city, onSuccess, onState)

    override suspend fun getCityLocal(city: String): List<WeatherUI> =
            local.getCityLocal(city).map { it.mapToWeatherUI() }

    override suspend fun deleteArrayCity(city: String) =
            local.deleteArrayCity(city)

    override suspend fun getCityListSaveLocal(): List<String> =
            local.getCityListSaveLocal()
}