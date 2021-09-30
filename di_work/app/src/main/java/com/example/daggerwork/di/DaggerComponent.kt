package com.example.daggerwork.di

import com.example.daggerwork.repository.BicycleRepository
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [DaggerModule::class],
)
interface DaggerComponent {
    fun bicycleRepo() : BicycleRepository
}