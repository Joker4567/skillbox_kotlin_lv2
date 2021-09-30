package com.example.daggerwork.dagger

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daggerwork.repository.BicycleRepository
import com.example.daggerwork.models.Bicycle
import com.example.daggerwork.models.Color
import com.example.daggerwork.models.Logo
import kotlinx.coroutines.launch

class BicycleViewModel (
    private val repository: BicycleRepository
) : ViewModel() {
    val profile = MutableLiveData<State>(State.Loading)

    init {
        viewModelScope.launch {
           val result = kotlin.runCatching { repository.create(Logo(), Color()) }.fold(
                onSuccess = { State.Data(it) },
                onFailure = {
                    State.Error(it)
                },
            )

            profile.postValue(result)
        }
    }

    sealed class State {
        object Loading : State()
        class Error(val throwable: Throwable) : State()
        class Data(val profile: Bicycle) : State()
    }
}