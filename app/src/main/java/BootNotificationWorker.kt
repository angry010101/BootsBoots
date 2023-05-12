package com.wave.periodicworkrequest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.simon.itestapp.BootRepository
import com.simon.itestapp.R

class BootNotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val db = BootRepository(context)

    private val noBootsStr = context.getString(R.string.no_boot)

    // TODO update boot message
    override fun doWork(): Result {

        val boots = db.getBoots()

        val taskDataString: String = when {
            boots?.size == 0 -> noBootsStr
            // TODO stringRes
            boots?.size == 1 -> "The boot was detected with the timestamp = ${boots[0].ts}"
            boots?.size != null && boots.size!! > 1 -> "Last boots time delta =" + " ${boots[boots.size - 1].ts - boots[boots.size - 2].ts}"
            else -> noBootsStr
        }


        showNotification("WorkManager", taskDataString ?: "Message has been Sent")
        val outputData: Data = Data.Builder().putString(WORK_RESULT, "Jobs Finished").build()
        return Result.success(outputData)
    }

    private fun showNotification(task: String, desc: String) {
        val manager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        val channelId = "task_channel"
        val channelName = "task_name"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, channelId
        ).setContentTitle(task).setContentText(desc)
            .setSmallIcon(android.R.drawable.ic_lock_power_off)
        manager.notify(1, builder.build())
    }

    companion object {
        private const val WORK_RESULT = "work_result"
    }
}