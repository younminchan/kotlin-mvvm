package com.example.mvvm_kotlin.repository

import com.example.mvvm_kotlin.dao.RoomDao
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.model.RoomModel
import com.example.mvvm_kotlin.network.RetrofitService
import retrofit2.Call

/** Repository: 하나 이상의 데이터 소스를 관리한다. */
class MainRepository(private var roomDao: RoomDao) {

    fun network_getPlaceholder(category: String): Call<ArrayList<PhotoDataItem>> {
        return RetrofitService.getInstance().getPlaceholder(category)
    }

    fun selectDB() = roomDao.getAll()
    fun insertDB(roomModel: RoomModel) = roomDao.insert(roomModel)
    fun deleteDB(roomModel: RoomModel) = roomDao.deleteItem(roomModel)

}