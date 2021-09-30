package com.example.daggerwork.repository

import com.example.daggerwork.models.*

class BicycleRepositoryImp(
    private val frame: Frame
) : BicycleRepository {
    override fun create(logo: Logo, color: Color): Bicycle {
        logo.id = (0..100).random().toString()
        return BicycleFactory(
            frame
        ).build(logo, color)
    }

}