package com.simon.itestapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.util.*

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1?.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            handleBootCompleted(p0)
        }
    }

    private fun handleBootCompleted(context: Context?) {
        context?.let {
            val thread = Thread {
                // TODO check whether we have ts in intent
                val ts = getTime()
                val bootRepository = BootRepository(context)
                bootRepository.addBoot(BootModel(ts))
            }
            thread.start()
            thread.join()
        }
    }

    private fun getTime(): Long = Date().time

}