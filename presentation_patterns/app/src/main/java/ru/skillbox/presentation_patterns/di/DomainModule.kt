package ru.skillbox.presentation_patterns.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.skillbox.presentation_patterns.data.network.repository.WeatherDaoRepository
import ru.skillbox.presentation_patterns.data.network.repository.WeatherNetworkRepository
import ru.skillbox.presentation_patterns.domain.WeatherDomain
import ru.skillbox.presentation_patterns.domain.WeatherDomainImpl

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {
    @Provides
    fun provideWeatherDomain(
            weatherDaoRepository: WeatherDaoRepository,
            weatherDomain: WeatherNetworkRepository
    ) : WeatherDomain {
        return WeatherDomainImpl(weatherDomain, weatherDaoRepository)
    }
}