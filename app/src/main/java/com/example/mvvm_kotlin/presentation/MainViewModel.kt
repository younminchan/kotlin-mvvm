package com.example.mvvm_kotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin.data.PhotoData
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private var repository = MainRepository()

//    private val _searchResult = MutableLiveData<ArrayList<PhotoDataItem>>()
//    val searchResult : LiveData<ArrayList<PhotoDataItem>>
//        get() = _searchResult
    private val _searchResult = MutableLiveData<String>()
    val searchResult : LiveData<String>
        get() = _searchResult

    init {
//        _searchResult.value = ArrayList<PhotoDataItem>
    }

    fun searchCategory(category: String){
        repository.network_getPlaceholder(category).enqueue(object : Callback<ArrayList<PhotoDataItem>> {
            override fun onResponse(call: Call<ArrayList<PhotoDataItem>>, response: Response<ArrayList<PhotoDataItem>>) {
                if(response.isSuccessful){
                    var res = response.body()
                    if(res!=null && res.size>0){
                        _searchResult.value = res.toString()
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<PhotoDataItem>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}