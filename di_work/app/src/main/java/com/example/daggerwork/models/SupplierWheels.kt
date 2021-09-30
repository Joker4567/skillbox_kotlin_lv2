package com.example.daggerwork.models

import kotlin.random.Random

class SupplierWheels {

    companion object {
        fun createWheels() : Wheels = Wheels(serialNumber = (50..100L).random())
    }
}