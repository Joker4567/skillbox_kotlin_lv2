package com.example.daggerwork

import android.app.Application
import com.example.daggerwork.repository.BicycleRepository
import com.example.daggerwork.repository.BicycleRepositoryImp
import com.example.daggerwork.di.DaggerComponent
import com.example.daggerwork.di.DaggerDaggerComponent
import com.example.daggerwork.models.FrameFactory
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {
    companion object {
        lateinit var component: DaggerComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerDaggerComponent.builder().build()

        startKoin {
            modules(module {
                factory { FrameFactory().build() }
                factory<BicycleRepository> { BicycleRepositoryImp(get()) }
            })
        }
    }
}