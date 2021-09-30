package com.example.compose.counter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class CounterViewModel : ViewModel() {
    val count = MutableStateFlow(0)
    val step = MutableStateFlow(0)
}