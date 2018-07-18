package com.vyakunin.testapp.app

import android.app.Application
import org.koin.android.ext.android.startKoin

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule))
    }
}