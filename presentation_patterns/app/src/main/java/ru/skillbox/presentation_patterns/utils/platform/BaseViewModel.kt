package ru.skillbox.presentation_patterns.utils.platform

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.skillbox.presentation_patterns.utils.extension.NavigationEvent

abstract class BaseViewModel : ViewModel() {

    val mainState = MutableLiveData<Event<State>>()
    val fragmentNavigation = MutableLiveData<Event<NavigationEvent>>()

    protected fun handleState(state: State) {
        if (state is State.Error) {
            mainState.value = Event(state)
        } else
            mainState.value = Event(state)
    }

    protected fun launch(func: suspend () -> Unit) =
            viewModelScope.launch(Dispatchers.Main) { func.invoke() }

    protected fun launchIO(func: suspend () -> Unit) =
            viewModelScope.launch(Dispatchers.IO) { func.invoke() }

    fun navigate(navigationEvent: NavigationEvent) {
        fragmentNavigation.value = Event(navigationEvent)
    }
}