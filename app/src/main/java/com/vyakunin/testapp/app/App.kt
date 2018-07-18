package com.vyakunin.testapp.app

import android.app.Application
import android.graphics.Bitmap
import com.squareup.picasso.Picasso
import com.vyakunin.testapp.sync.command.SyncTopTenMoviesCommand
import org.koin.android.ext.android.startKoin

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(appModule, repoModule, presentationModule))
        scheduleSync()

        Picasso.setSingletonInstance(Picasso.Builder(this).defaultBitmapConfig(Bitmap.Config.RGB_565).build())
    }

    private fun scheduleSync() {
        SyncTopTenMoviesCommand().schedule()
    }
}