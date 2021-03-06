package com.example.mvvm_kotlin.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.mvvm_kotlin.model.RoomModel

@Dao
interface RoomDao {

    @Query("SELECT * FROM mvvm")
    fun getAll(): LiveData<List<RoomModel>>

    @Insert(onConflict = REPLACE)
    fun insert(roomModel: RoomModel)

    @Delete
    fun deleteItem(roomModel: RoomModel)

    @Query("DELETE FROM mvvm WHERE id = :id")
    fun deleteItem2(id: Int)

    @Query("DELETE FROM mvvm")
    fun deleteAll()

}