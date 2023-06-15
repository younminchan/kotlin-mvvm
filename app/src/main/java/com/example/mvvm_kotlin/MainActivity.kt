package com.example.mvvm_kotlin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvm_kotlin.adapter.RvRoomAdatper
import com.example.mvvm_kotlin.databinding.ActivityMainBinding
import com.example.mvvm_kotlin.model.RoomModel
import com.example.mvvm_kotlin.presentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //(중요)이걸로 사용하기
//        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel = MainViewModel.getInstance("viewModel")

        // 상단바 없애기
//        supportActionBar?.hide()

        // 데이터바인딩(DataBinding)설정
        binding.lifecycleOwner = this //데이터바인딩 Lifecycle에 종속, LifeCycle_Observe역할
        binding.mainActivity = this
        binding.mainViewModel = mainViewModel

        //데이터베이스
        roomInit()

    }

    //서버 통신
    fun searchClick() {
        mainViewModel.searchCategory("list")
    }

    //Room 초기설정
    fun roomInit() {
        var roomAdapter = RvRoomAdatper(mainViewModel)
        binding.rvRoom.apply {
            layoutManager = LinearLayoutManager(App.activity)
            adapter = roomAdapter
            setHasFixedSize(true)
        }
        mainViewModel.roomResult.observe(this, Observer {
            roomAdapter.setItem(it)
        })
    }

    fun insertDB() {
        var name = binding.etName.text.toString()
        var age = binding.etAge.text.toString()

        if (name.isNotEmpty() && age.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                var insertRoomModel = RoomModel(0, name, age)
                mainViewModel.insertDB(insertRoomModel)

                withContext(Dispatchers.Main) {
                    Toast.makeText(App.context, "RoomDB insert완료", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(App.context, "Name, age 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    fun changeLayout(tab: String) {
        if (tab.equals("retrofit")) {
            binding.clRetrofit.visibility = View.VISIBLE
            binding.clRoom.visibility = View.GONE
        } else {
            binding.clRetrofit.visibility = View.GONE
            binding.clRoom.visibility = View.VISIBLE
        }
    }

}