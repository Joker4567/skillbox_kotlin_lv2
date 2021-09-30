package ru.skillbox.presentation_patterns.ui.fragment.search

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ru.skillbox.presentation_patterns.data.network.repository.WeatherRepository
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.data.storage.Pref
import ru.skillbox.presentation_patterns.ui.fragment.detail.DetailCityFragment
import ru.skillbox.presentation_patterns.utils.*
import ru.skillbox.presentation_patterns.utils.extension.NavigationEvent
import ru.skillbox.presentation_patterns.utils.platform.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
        private val pref: Pref,
        private val weatherRepository: WeatherRepository
) : BaseViewModel() {

    private val _weatherStatus =
            SingleLiveEvent<String>()

    val weatherStatus: SingleLiveEvent<String>
        get() = _weatherStatus

    private var lastCity: String = ""

    fun getCity(city: String) {
        lastCity = city
        launchIO {
            weatherRepository.getWeatherCity(city, ::onSuccess, ::onError)
        }
    }

    private fun onSuccess(result: List<WeatherEntity>) {
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
                    val isSuccess = weatherRepository.getCityLocal(lastCity).isNotEmpty()
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

    fun navigateDetail(city: String) {
        navigate(NavigationEvent.PushFragment(DetailCityFragment.newInstance(city)))
    }
}