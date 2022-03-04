package com.example.mvvm_kotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private var repository = MainRepository()

    private val _searchResult = MutableLiveData<ArrayList<PhotoDataItem>>()
    val searchResult : LiveData<ArrayList<PhotoDataItem>>
        get() = _searchResult
    private var photoItems = ArrayList<PhotoDataItem>()

    init {
        photoItems = arrayListOf()
        _searchResult.value = arrayListOf()
    }

    fun searchCategory(category: String){
        repository.network_getPlaceholder(category).enqueue(object : Callback<ArrayList<PhotoDataItem>> {
            override fun onResponse(call: Call<ArrayList<PhotoDataItem>>, response: Response<ArrayList<PhotoDataItem>>) {
                if(response.isSuccessful){
                    var res = response.body()
                    if(res!=null && res.size>0){
//                        _searchResult.value = res!!
                        insertPhotoItems(res)
                        Log.e("YMC", "res size: ${res.size}")
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<PhotoDataItem>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    fun insertPhotoItems(arr:ArrayList<PhotoDataItem>){
        photoItems.clear()
        photoItems.addAll(arr)
        _searchResult.value = photoItems
    }
}