package com.vyakunin.testapp.sync.command

import android.util.Log
import com.vyakunin.testapp.repo.MovieRepo
import com.vyakunin.testapp.sync.TestJobService
import org.koin.android.ext.android.get
import java.io.IOException

class SyncTopTenMoviesCommand : BaseCommand() {

    companion object {
        const val TAG = "SyncTopTenMoviesCommand"
    }

    override val jobId: Int = TestJobService.JOB_ID_TOP_TEN

    override fun runForResult(): Boolean {
        Log.e("!!!", "SyncTopTenMoviesCommand started")
        val call = api.moviesByRank(1, 10)
        try {
            val response = call.execute()
            val dtoList = response.body()
            if (response.isSuccessful && dtoList != null) {
                val repo = appContext.get<MovieRepo>()
                repo.deleteAll()
                repo.insertUpdateRank(dtoList)
                if (repo.getEmptyMovieIds().isNotEmpty()) {
                    SyncMovieDetailsCommand().schedule()
                }
            }
            return true//no need to reschedule
        } catch (e: IOException) {
            Log.e(TAG, "Network error", e)
        }
        return false
    }
}