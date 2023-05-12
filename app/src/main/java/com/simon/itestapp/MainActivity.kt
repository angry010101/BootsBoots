package com.simon.itestapp

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.wave.periodicworkrequest.BootNotificationWorker
import java.util.concurrent.TimeUnit

//TODO architecture
class MainActivity : AppCompatActivity() {

    companion object {
        private const val BOOT_NOTIF_SHOW_TIME: Long = 15
    }

    private val viewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scheduleNotificationTask()
        viewModel.boots.observe(this, Observer {
            updateCounter(it)
        })
        viewModel.requestBoots()
    }

    private fun scheduleNotificationTask() {
        val mWorkManager = WorkManager.getInstance(this)
        mWorkManager.cancelAllWork()
        val workRequest = prepareWorkRequest()
        mWorkManager.enqueue(workRequest)
    }

    private fun prepareWorkRequest() =
        PeriodicWorkRequest.Builder(
            BootNotificationWorker::class.java,
            BOOT_NOTIF_SHOW_TIME,
            TimeUnit.MINUTES
        ).build()

    private fun updateCounter(boots: List<BootModel>) {
        findViewById<TextView>(R.id.text_view).text =
            if (boots.isEmpty()) {
                getString(R.string.no_boot)
            } else {
                prepareBootsString(boots)
            }
    }

    private fun prepareBootsString(boots: List<BootModel>): String {
        var bootsString = ""
        boots.forEachIndexed { ind, boot ->
            bootsString += "${ind}: ${boot.ts}\n"
        }
        return bootsString
    }

}