package com.example.mvvm_kotlin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mvvm")
class RoomModel(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "age") var age: String,
) {
    constructor() : this(0, "", "")
}