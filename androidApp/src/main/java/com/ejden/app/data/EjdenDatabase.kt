package com.ejden.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1,
    exportSchema = false
)
abstract class EjdenDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: EjdenDatabase? = null

        fun getInstance(context: Context): EjdenDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    EjdenDatabase::class.java,
                    "ejden_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
