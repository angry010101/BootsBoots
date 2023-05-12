package com.simon.itestapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BootDao {
    @Insert
    fun insertAll(vararg boot: BootModel)

    @Query("SELECT * FROM boots")
    fun getAll(): List<BootModel>
}

