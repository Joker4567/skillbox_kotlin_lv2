package com.example.daggerwork.di

import com.example.daggerwork.repository.BicycleRepository
import com.example.daggerwork.repository.BicycleRepositoryImp
import com.example.daggerwork.models.Frame
import com.example.daggerwork.models.FrameFactory
import dagger.Module
import dagger.Provides

@Module
class DaggerModule() {

    @Provides
    fun createFrameFactory(): Frame = FrameFactory().build()

    @Provides
    fun provideBicycle() : BicycleRepository = BicycleRepositoryImp(createFrameFactory())
}