package com.simon.itestapp

import android.content.Context

class BootRepository(context: Context) {
    private val db = BootDatabase.getInstance(context)

    fun addBoot(bootModel: BootModel) {
        db.bootDao()?.insertAll(bootModel)
    }

    fun getBoots() = db.bootDao()?.getAll()

}