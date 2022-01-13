package ru.skillbox.presentation_patterns.ui.fragment.search

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.skillbox.presentation_patterns.data.storage.Pref
import ru.skillbox.presentation_patterns.domain.WeatherDomain
import ru.skillbox.presentation_patterns.domain.model.WeatherUI
import ru.skillbox.presentation_patterns.ui.fragment.detail.DetailCityFragment
import ru.skillbox.presentation_patterns.utils.*
import ru.skillbox.presentation_patterns.utils.extension.NavigationEvent
import ru.skillbox.presentation_patterns.utils.platform.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
        private val pref: Pref,
        private val weatherDomain: WeatherDomain
) : BaseViewModel() {

    private val _weatherStatus =
            SingleLiveEvent<String>()

    val weatherStatus: SingleLiveEvent<String>
        get() = _weatherStatus

    private var lastCity: String = ""

    fun getCity(city: String) {
        lastCity = city
        launchIO {
            val result = weatherDomain.getWeatherCity(city, ::onError)
            onUpdateWeather(result)
        }
    }

    private fun onUpdateWeather(result: List<WeatherUI>) {
        launch {
            if (result.isNotEmpty()) {
                _weatherStatus.postValue(SUCCESS_WEATHER)
                delay(2000)
                navigateDetail(lastCity)
            }
        }
    }

    private fun onError(state: State) {
        when(state) {
            is State.Error -> {
                launchIO {
                    val isSuccess = weatherDomain.getCityLocal(lastCity).isNotEmpty()
                    if(isSuccess) {
                        _weatherStatus.postValue(LOCAL_WEATHER)
                        delay(2000)
                        navigateDetail(lastCity)
                    } else {
                        _weatherStatus.postValue(ERROR_WEATHER)
                    }
                }
            }
            is State.Loading -> {
                _weatherStatus.postValue(WAIT_WEATHER)
            }
            is State.Loaded -> {

            }
        }
    }

    fun getListCity() = pref.getListCity()

    fun saveCity(city: String) {
        pref.setApp(city, isAdd = true)
    }

    private fun navigateDetail(city: String) {
        navigate(NavigationEvent.PushFragment(DetailCityFragment.newInstance(city)))
    }
}