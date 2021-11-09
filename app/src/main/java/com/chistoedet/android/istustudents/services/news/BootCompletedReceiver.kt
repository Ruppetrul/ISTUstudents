package com.chistoedet.android.istustudents.services.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.work.*
import java.util.concurrent.TimeUnit

val NOTIFY_WORK = "NOTIFY_WORK"
class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicRequest =
            PeriodicWorkRequest.Builder(NewsPollingWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            NOTIFY_WORK,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRequest)

        val toast = Toast.makeText(context,"Деканат бдит!", Toast.LENGTH_LONG)
        Log.d("RECEIVER", "процесс запущен")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            context?.mainExecutor?.execute {
                toast.show()
            }
        } else {
            toast.show()
        }
    }
}