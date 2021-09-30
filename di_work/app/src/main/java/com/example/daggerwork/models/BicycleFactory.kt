package com.example.daggerwork.models

class BicycleFactory(
    private val frame: Frame
) {
    fun build(logo: Logo, color: Color) : Bicycle {
        val wheels = SupplierWheels.createWheels()
        val wheelsTwo = SupplierWheels.createWheels()
        return Bicycle(
            logo = logo,
            frame = frame,
            wheels = wheels,
            wheelsTwo = wheelsTwo
        )
    }
}