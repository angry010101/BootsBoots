package com.simon.itestapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(version = 1, entities = [BootModel::class])
abstract class BootDatabase : RoomDatabase() {
    // BookDao is a class annotated with @Dao.
    abstract fun bootDao(): BootDao?

    companion object {
        private var instance: BootDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): BootDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    ctx, BootDatabase::class.java,
                    "boot_database"
                ).build()

            return instance!!

        }

    }

}