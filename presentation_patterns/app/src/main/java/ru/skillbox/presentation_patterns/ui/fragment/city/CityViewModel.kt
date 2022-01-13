package ru.skillbox.presentation_patterns.ui.fragment.city

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.skillbox.presentation_patterns.domain.WeatherDomain
import ru.skillbox.presentation_patterns.ui.fragment.detail.DetailCityFragment
import ru.skillbox.presentation_patterns.utils.extension.NavigationEvent
import ru.skillbox.presentation_patterns.utils.platform.BaseViewModel
import ru.skillbox.presentation_patterns.utils.platform.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class CityViewModel @Inject constructor(
        private val weatherDomain: WeatherDomain
) : BaseViewModel() {
    private val _weatherList =
            SingleLiveEvent<List<String>>()

    val weatherList: SingleLiveEvent<List<String>>
        get() = _weatherList

    fun getCityLocalList() {
        launchIO {
            val localCity = weatherDomain.getCityListSaveLocal()
            launch {
                _weatherList.postValue(localCity)
            }
        }
    }

    fun navigateDetail(city: String) {
        navigate(NavigationEvent.PushFragment(DetailCityFragment.newInstance(city)))
    }
}