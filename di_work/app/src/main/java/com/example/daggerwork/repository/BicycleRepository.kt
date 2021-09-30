package com.example.daggerwork.repository

import com.example.daggerwork.models.*

interface BicycleRepository {
    fun create(logo: Logo, color: Color) : Bicycle
}