package com.example.mvvm_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_kotlin.databinding.ActivityMainBinding
import com.example.mvvm_kotlin.model.RoomModel
import com.example.mvvm_kotlin.presentation.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // 데이터바인딩(DataBinding)설정
        binding.lifecycleOwner = this //데이터바인딩 Lifecycle에 종속, LifeCycle_Observe역할
        binding.mainActivity = this
        binding.mainViewModel = mainViewModel
    }

    //서버 통신
    fun searchClick(){
        mainViewModel.searchCategory("list")
    }

    //데이터베이스
    fun selectDB(){
        var test: List<RoomModel>? = null
        CoroutineScope(Dispatchers.IO).launch {
            test = mainViewModel.selectDB()
            for (item in test!!) {
                Log.e("YMC", "test: ${item.id} / ${item.name} / ${item.age}")
            }
        }.run {
            Toast.makeText(applicationContext, "SelectDB: $test", Toast.LENGTH_SHORT).show()
        }
    }

    fun insertDB() {
        var name = binding.etName.text.toString()
        var age = binding.etAge.text.toString()

        if (name.isNotEmpty() && age.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                var insertRoomModel = RoomModel(0, name, age)
                mainViewModel.insertDB(insertRoomModel)
            }
        } else {
            Toast.makeText(App.context, "Name, age 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}