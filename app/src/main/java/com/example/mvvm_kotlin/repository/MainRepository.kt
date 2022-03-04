package com.example.mvvm_kotlin.repository

import com.example.mvvm_kotlin.data.PhotoData
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.network.RetrofitService
import retrofit2.Call

class MainRepository {
    fun network_getPlaceholder(category: String): Call<ArrayList<PhotoDataItem>> {
        return RetrofitService.getInstance().getPlaceholder(category)
    }
}