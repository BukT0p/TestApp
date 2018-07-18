package com.vyakunin.testapp.sync

import android.app.job.JobInfo
import android.app.job.JobParameters
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.content.Context
import android.os.PersistableBundle
import android.util.Log
import com.vyakunin.testapp.sync.command.BaseCommand
import com.vyakunin.testapp.sync.command.SyncMovieDetailsCommand
import com.vyakunin.testapp.sync.command.SyncTopTenMoviesCommand
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

class TestJobService : JobService() {
    companion object {
        const val TAG = "TestJobService"

        private val bgScheduler = Schedulers.from(ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 3,
                1,
                TimeUnit.MINUTES,
                LinkedBlockingQueue()))

        fun scheduleJob(context: Context, jobId: Int, delay: Long = 0L, extras: PersistableBundle? = null) {
            val builder = JobInfo.Builder(jobId, ComponentName(context, TestJobService::class.java))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setMinimumLatency(delay)//ASAP
                    .setOverrideDeadline(delay + 3000L)
            if (extras != null) builder.setExtras(extras)
            val jobInfo = builder.build()

            (context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).schedule(jobInfo)
        }

        const val JOB_ID_TOP_TEN = 1
        const val JOB_ID_DETAILS = 2
    }

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun onStopJob(params: JobParameters?): Boolean = false

    override fun onStartJob(params: JobParameters?): Boolean {
        if (params == null) return false //do not need to work
        val jobId = params.jobId
        val command: BaseCommand = when (jobId) {
            JOB_ID_TOP_TEN -> SyncTopTenMoviesCommand()
            JOB_ID_DETAILS -> SyncMovieDetailsCommand()
            else -> return false //do not need to work
        }
        Single
                .create<Pair<JobParameters, Boolean>> {
                    val result = command.runForResult()
                    it.onSuccess(params to result)
                }.subscribeOn(bgScheduler)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy({
                    Log.e(TAG, "JobId : $jobId failed", it)
                }, {
                    val jobParams = it.first
                    val result = it.second
                    jobFinished(jobParams, !result)
                }).addTo(subscriptions)
        return true
    }

    override fun onDestroy() {
        subscriptions.clear()
        super.onDestroy()
    }
}