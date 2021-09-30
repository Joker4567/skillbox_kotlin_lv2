package ru.skillbox.presentation_patterns.ui.fragment.detail.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_weather.view.*
import ru.skillbox.presentation_patterns.R
import ru.skillbox.presentation_patterns.data.room.model.WeatherEntity

fun itemDetailWeather() =
        adapterDelegateLayoutContainer<WeatherEntity, Any>(R.layout.item_weather) {

            bind {
                containerView.item_date.text = item.day
                containerView.item_temperature.text = item.temperature
            }
        }