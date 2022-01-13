package ru.skillbox.presentation_patterns.utils.extension

interface CallbackC<T> {
    fun onSuccess(model: T)
    fun onFailure(exception: Throwable)
}