package com.example.mvvm_kotlin.network

import com.example.mvvm_kotlin.data.PhotoDataItem
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type

interface RetrofitService {

//    @Headers("Content-Type: application/json")
    @GET("{category}") //photos
    fun getPlaceholder(
        @Path("category") category:String
    ): Call<ArrayList<PhotoDataItem>>

    companion object {
        var networkService: RetrofitService? = null
        var baseUrl = "https://picsum.photos/v2/"

        fun getInstance(): RetrofitService {
            if (networkService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(nullOnEmptyConverterFactory)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                networkService = retrofit.create(RetrofitService::class.java)
            }
            return networkService!!
        }

        /** 비어있는(length=0)인 Response를 받았을 경우 처리 */
        private val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
                override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) {
                    try{
                        nextResponseBodyConverter.convert(value)
                    }catch (e:Exception){
                        e.printStackTrace()
                        null
                    }
                } else{
                    null
                }
            }
        }
    }
}