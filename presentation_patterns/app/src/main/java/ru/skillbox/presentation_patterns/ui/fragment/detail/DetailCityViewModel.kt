package ru.skillbox.presentation_patterns.ui.fragment.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.skillbox.presentation_patterns.data.network.repository.WeatherRepository
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.utils.extension.NavigationEvent
import ru.skillbox.presentation_patterns.utils.platform.BaseViewModel
import ru.skillbox.presentation_patterns.utils.platform.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class DetailCityViewModel @Inject constructor(
        private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    private val _weatherList =
            SingleLiveEvent<List<WeatherEntity>>()

    val weatherList: SingleLiveEvent<List<WeatherEntity>>
        get() = _weatherList

    fun navigateBack() {
        navigate(NavigationEvent.Exit)
    }

    fun getListWeather(city: String) {
        launchIO {
            val cityResult = weatherRepository.getCityLocal(city)
            launch {
                _weatherList.postValue(cityResult)
            }
        }
    }

    fun removeCity(city: String) {
        launchIO {
            weatherRepository.deleteArrayCity(city)
            launch {
                navigateBack()
            }
        }
    }
}