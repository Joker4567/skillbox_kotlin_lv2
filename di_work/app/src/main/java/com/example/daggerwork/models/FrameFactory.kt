package com.example.daggerwork.models

import kotlin.random.Random

class FrameFactory {
    fun build() : Frame {
        val color = Color()
        color.RGB = (0..100).random()
        val frame = Frame(color)
        frame.serialNumber = (0..10000L).random()
        return frame
    }
}