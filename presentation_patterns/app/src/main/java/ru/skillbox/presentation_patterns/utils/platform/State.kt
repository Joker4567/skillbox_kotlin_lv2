package ru.skillbox.presentation_patterns.utils.platform

import ru.skillbox.presentation_patterns.utils.network.Failure

sealed class State {
    object Loading : State()
    object Loaded : State()
    data class Error(val throwable: Failure) : State()
}