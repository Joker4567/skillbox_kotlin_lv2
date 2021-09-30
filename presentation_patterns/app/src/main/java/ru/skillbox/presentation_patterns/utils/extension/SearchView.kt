package ru.skillbox.presentation_patterns.utils.extension

import androidx.appcompat.widget.SearchView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.*

fun SearchView.textChangedFlow() : Flow<String> {
    return callbackFlow {
        val textChanged = object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //вызовется при нажатии на лупу на клавиатуре
                sendBlocking("${query.orEmpty()}!")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //вызовется при изменении ведённого текста
                sendBlocking(newText.orEmpty())
                return true
            }
        }
        this@textChangedFlow.setOnQueryTextListener(textChanged)
        awaitClose {
            this@textChangedFlow.setOnQueryTextListener(null)
        }
    }
}