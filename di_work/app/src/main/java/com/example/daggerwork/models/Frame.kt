package com.example.daggerwork.models

class Frame(private val color: Color) {
    var serialNumber: Long = 0L

    fun getColorIdFrame() : String = "$serialNumber - ${color.RGB}"
}