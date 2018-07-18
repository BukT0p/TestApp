package com.vyakunin.testapp.app

import android.graphics.Bitmap
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import com.vyakunin.testapp.BuildConfig
import com.vyakunin.testapp.api.IMovieApi
import com.vyakunin.testapp.api.MovieApiWrapper
import com.vyakunin.testapp.data.MyObjectBox
import com.vyakunin.testapp.repo.MovieRepo
import io.objectbox.BoxStore
import okhttp3.OkHttpClient
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule: Module = applicationContext {
    // Provides app instance
    bean { App.instance }

    //Provides Gson
    bean { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() as Gson }

    //Provides Http Client
    bean { OkHttpClient() }

    //Provides Api Gateway
    bean {
        val api = Retrofit.Builder()
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .baseUrl("https://interview.zocdoc.com/api/1/FEE/").build()
                .create(IMovieApi::class.java)
        MovieApiWrapper(api = api, authToken = BuildConfig.AuthToken)
    }

    //Provides single picasso instance
    bean { Picasso.Builder(get()).defaultBitmapConfig(Bitmap.Config.RGB_565).build() }
}

val repoModule: Module = applicationContext {
    bean { MyObjectBox.builder().androidContext(get()).build() as BoxStore }
    factory { MovieRepo(get()) }
}