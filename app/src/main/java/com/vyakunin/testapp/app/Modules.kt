package com.vyakunin.testapp.app

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vyakunin.testapp.BuildConfig
import com.vyakunin.testapp.api.IMovieApi
import com.vyakunin.testapp.api.MovieApiWrapper
import com.vyakunin.testapp.data.MyObjectBox
import com.vyakunin.testapp.interactor.DetailsInteractor
import com.vyakunin.testapp.interactor.IDetailsInteractor
import com.vyakunin.testapp.interactor.IMainInteractor
import com.vyakunin.testapp.interactor.MainInteractor
import com.vyakunin.testapp.presentation.details.DetailsFragment
import com.vyakunin.testapp.presentation.details.DetailsPresenter
import com.vyakunin.testapp.presentation.main.MainPresenter
import com.vyakunin.testapp.repo.MovieRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
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
}

val repoModule: Module = applicationContext {
    bean("boxStore") { MyObjectBox.builder().androidContext(this.androidApplication().applicationContext).build() }
    factory { MovieRepo(get("boxStore")) }
}

val presentationModule: Module = applicationContext {
    bean("bg") { Schedulers.io() }
    bean("ui") { AndroidSchedulers.mainThread() }

    factory { MainInteractor(get(), get()) as IMainInteractor }
    factory { MainPresenter(interactor = get(), uiScheduler = get("ui"), bgScheduler = get("bg")) }

    factory { DetailsInteractor(get(), get()) as IDetailsInteractor }
    factory { params ->
        val movieId: Long = params[DetailsFragment.ARG_ID] ?: 0
        DetailsPresenter(movieId = movieId, interactor = get(), uiScheduler = get("ui"), bgScheduler = get("bg"))
    }

}