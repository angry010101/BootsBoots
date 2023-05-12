package com.simon.itestapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "boots")
data class BootModel(
    @PrimaryKey val ts: Long
)
