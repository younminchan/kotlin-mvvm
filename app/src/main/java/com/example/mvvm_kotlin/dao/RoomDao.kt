package com.example.mvvm_kotlin.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.mvvm_kotlin.model.RoomModel

@Dao
interface RoomDao {

    @Query("SELECT * FROM mvvm")
    fun getAll(): List<RoomModel>

    @Insert(onConflict = REPLACE)
    fun insert(roomModel: RoomModel)

    @Query("DELETE FROM mvvm")
    fun deleteAll()
}