package ru.skillbox.presentation_patterns.utils.extension

import java.util.*

fun getDateCurrent(): String {
    val calendar = Calendar.getInstance()
    return "${calendar[Calendar.DAY_OF_MONTH]}.${calendar[Calendar.MONTH]}.${calendar[Calendar.YEAR]}"
}