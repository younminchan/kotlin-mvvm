package com.example.mvvm_kotlin.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin.App
import com.example.mvvm_kotlin.dao.RoomDB
import com.example.mvvm_kotlin.dao.RoomDao
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.model.RoomModel
import com.example.mvvm_kotlin.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    /** Repository */
    var repository: MainRepository
    /** Room */
    private var roomDao = RoomDB.getInstance(App.context)!!.roomDao()

    //검색결과
    private var _searchResult = MutableLiveData<ArrayList<PhotoDataItem>>()
    val searchResult : LiveData<ArrayList<PhotoDataItem>>
        get() = _searchResult

    //Room저장데이터
    val roomResult : LiveData<List<RoomModel>>
        get() = repository.selectDB()

    //사진
    private var photoItems = ArrayList<PhotoDataItem>()

    init {
        repository = MainRepository(roomDao)

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

//    fun selectDB() = repository.selectDB()
    fun insertDB(roomModel: RoomModel){
        repository.insertDB(roomModel)
    }
}