package com.vyakunin.testapp.sync.command

import com.vyakunin.testapp.api.MovieApiWrapper
import com.vyakunin.testapp.app.App
import com.vyakunin.testapp.sync.TestJobService
import org.koin.android.ext.android.get

abstract class BaseCommand() {
    val appContext = App.instance
    val api = appContext.get<MovieApiWrapper>()

    /**
     * @return false if you want failed command to be re-scheduled
     */
    abstract fun runForResult(): Boolean

    abstract val jobId: Int

    fun schedule() {
        TestJobService.scheduleJob(appContext, jobId)
    }
}