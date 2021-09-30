package ru.skillbox.presentation_patterns.utils.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import ru.skillbox.presentation_patterns.utils.platform.*

fun <T : Any?, L : LiveData<Event<T>>> AppCompatActivity.observeEvent(
        liveData: L,
        body: (T) -> Unit
) =
        liveData.observe(this, EventObserver(body))

fun <T : Any?, L : LiveData<Event<T>>> Fragment.observeEvent(liveData: L, body: (T) -> Unit) =
        liveData.observe(viewLifecycleOwner, EventObserver(body))