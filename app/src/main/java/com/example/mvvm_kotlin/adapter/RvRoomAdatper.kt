package com.example.mvvm_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_kotlin.data.PhotoDataItem
import com.example.mvvm_kotlin.databinding.RvItemBinding
import com.example.mvvm_kotlin.databinding.RvRoomItemBinding
import com.example.mvvm_kotlin.model.RoomModel

class RvRoomAdatper() : RecyclerView.Adapter<RvRoomAdatper.MyViewHolder>() {
    var itemList = listOf<RoomModel>()

    class MyViewHolder(private val binding: RvRoomItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RoomModel) {
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvRoomItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    fun setItem(items: List<RoomModel>){
        itemList = items
        notifyDataSetChanged()
    }
}