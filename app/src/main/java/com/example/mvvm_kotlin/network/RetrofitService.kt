package com.example.mvvm_kotlin.network

import com.example.mvvm_kotlin.data.PhotoData
import com.example.mvvm_kotlin.data.PhotoDataItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface RetrofitService {

//    @Headers("Content-Type: application/json")
    @GET("{category}") //photos
    fun getPlaceholder(
        @Path("category") category:String
    ): Call<ArrayList<PhotoDataItem>>

    companion object {
        var networkService: RetrofitService? = null
        var baseUrl = "https://jsonplaceholder.typicode.com/"

        fun getInstance(): RetrofitService {
            if (networkService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                networkService = retrofit.create(RetrofitService::class.java)
            }
            return networkService!!
        }
    }
}