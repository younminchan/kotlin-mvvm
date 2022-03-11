package com.example.mvvm_kotlin.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mvvm_kotlin.model.RoomModel

@Database(entities = [RoomModel::class], version = 1)
abstract class RoomDB: RoomDatabase() {
    abstract fun roomDao(): RoomDao

    companion object {
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB? {
            if (INSTANCE == null) {
                synchronized(RoomDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, RoomDB::class.java, "room.db")
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}