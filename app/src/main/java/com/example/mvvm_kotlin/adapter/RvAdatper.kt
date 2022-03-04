package com.example.mvvm_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.databinding.RvItemBinding

class RvAdatper() : RecyclerView.Adapter<RvAdatper.MyViewHolder>() {
    var itemList = arrayListOf<PhotoDataItem>()

    class MyViewHolder(private val binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PhotoDataItem) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    /** 깜빡임 방지
     * 1. getItemId로 지정
     * 2. Adapter에 setHasStableIds(true) 지정
     * */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}