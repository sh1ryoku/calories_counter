package com.example.foodcounter.app

import android.app.Application
import com.example.foodcounter.di.dataModule
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            modules(
                modules = dataModule
            )
        }
    }
}