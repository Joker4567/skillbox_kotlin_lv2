package ru.skillbox.presentation_patterns.ui.fragment.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.domain.WeatherDomain
import ru.skillbox.presentation_patterns.domain.model.WeatherUI
import ru.skillbox.presentation_patterns.utils.extension.NavigationEvent
import ru.skillbox.presentation_patterns.utils.platform.BaseViewModel
import ru.skillbox.presentation_patterns.utils.platform.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class DetailCityViewModel @Inject constructor(
        private val weatherDomain: WeatherDomain
) : BaseViewModel() {

    private val _weatherList =
            SingleLiveEvent<List<WeatherUI>>()

    val weatherList: SingleLiveEvent<List<WeatherUI>>
        get() = _weatherList

    fun navigateBack() {
        navigate(NavigationEvent.Exit)
    }

    fun getListWeather(city: String) {
        launchIO {
            val cityResult = weatherDomain.getCityLocal(city)
            launch {
                _weatherList.postValue(cityResult)
            }
        }
    }

    fun removeCity(city: String) {
        launchIO {
            weatherDomain.deleteArrayCity(city)
            launch {
                navigateBack()
            }
        }
    }
}