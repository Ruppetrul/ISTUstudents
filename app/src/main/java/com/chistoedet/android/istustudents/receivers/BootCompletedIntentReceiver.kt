package com.chistoedet.android.istustudents.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.work.*
import com.chistoedet.android.istustudents.services.news.NewsPollingWorker
import java.util.concurrent.TimeUnit




class BootCompletedIntentReceiver : BroadcastReceiver() {

    private val POLL_WORK = "POLL_WORK"

    override fun onReceive(context: Context?, intent: Intent?) {

        if (Intent.ACTION_BOOT_COMPLETED == intent?.action) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                context?.mainExecutor?.execute {
                    Toast.makeText(context,"onReceive", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, "onReceive", Toast.LENGTH_LONG).show()
            }

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()

            val periodicRequest =
            PeriodicWorkRequest.Builder(NewsPollingWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

            WorkManager.getInstance().enqueueUniquePeriodicWork(
                POLL_WORK,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicRequest)
        }
    }
}