package ru.skillbox.presentation_patterns.utils.network

sealed class Failure {
    object ServerError : Failure()
    object CommonError : Failure()
    object UnknownError : Failure()
}