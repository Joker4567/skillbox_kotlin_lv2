package com.simplemobiletools.clock.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

object StatePermissionLocation {
    private val mutableStateLocation = MutableSharedFlow<Boolean>()
    val isSuccessLocation: SharedFlow<Boolean> = mutableStateLocation

    fun changeStateAppInstall(isSuccess: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            mutableStateLocation.emit(isSuccess)
        }
    }
}


