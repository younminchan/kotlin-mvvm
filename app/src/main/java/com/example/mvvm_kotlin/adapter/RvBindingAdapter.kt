package com.example.mvvm_kotlin.adapter

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mvvm_kotlin.App
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.model.RoomModel

object RvBindingAdapter {
    @BindingAdapter("rv_retrofit_items")
    @JvmStatic
    fun rv_retrofit_items(recyclerView: RecyclerView, items: ArrayList<PhotoDataItem>){
        if(recyclerView.adapter == null){
            recyclerView.layoutManager = LinearLayoutManager(App.activity)
            val myAdapter = RvRetrofitAdatper()
            recyclerView.adapter = myAdapter
//            myAdapter.setHasStableIds(true)
        }

        val myAdapter = recyclerView.adapter as RvRetrofitAdatper
        myAdapter.itemList = items
        myAdapter.notifyDataSetChanged()
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        Log.e("YMC", "url: ${url}")
        Glide.with(imageView.context)
            .load(url).override(200, 200).thumbnail(0.1f)
            .diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.ic_launcher_background)
            .dontAnimate().into(imageView)
    }
}