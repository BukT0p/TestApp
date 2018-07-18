package com.vyakunin.testapp.sync.command

import android.util.Log
import com.vyakunin.testapp.repo.MovieRepo
import com.vyakunin.testapp.sync.TestJobService
import org.koin.android.ext.android.get
import java.io.IOException

class SyncMovieDetailsCommand : BaseCommand() {
    companion object {
        const val TAG = "SyncMovieDetailsCommand"
    }

    override val jobId: Int = TestJobService.JOB_ID_DETAILS

    override fun runForResult(): Boolean {
        val repo = appContext.get<MovieRepo>()
        val emptyMovieIds = repo.getEmptyMovieIds()
        if (emptyMovieIds.isEmpty()) return true
        val call = api.moviesDetails(emptyMovieIds)
        try {
            val response = call.execute()
            val dtoList = response.body()
            if (response.isSuccessful && dtoList != null) {

            }
            return true
        } catch (e: IOException) {
            Log.e(TAG, "Network error", e)
        }
        return false
    }
}