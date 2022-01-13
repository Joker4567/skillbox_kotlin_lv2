package ru.skillbox.presentation_patterns.domain.mapper

import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity
import ru.skillbox.presentation_patterns.domain.model.WeatherUI

fun WeatherEntity.mapToWeatherUI() =
        WeatherUI(
            id, temperature, description, city, day
        )