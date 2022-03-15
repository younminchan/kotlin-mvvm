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
import kotlinx.coroutines.withContext

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
        var items: List<RoomModel>? = null
        CoroutineScope(Dispatchers.IO).launch {
            var temp = mainViewModel.selectDB()

            for (item in temp!!) {
                Log.e("YMC", "test: ${item.id} / ${item.name} / ${item.age}")
            }

            /** Coroutine에서 처리할거 다 처리하고 Main스레드에서 처리할 내용들 처리!(ToastMessage 등) */
            withContext(Dispatchers.Main){
                items = temp

                var printItems = ""
                for(item in items!!){
                    printItems += "${item.id} / ${item.name} / ${item.age} \n"
                }
                Toast.makeText(applicationContext, "SelectDB:\n $printItems", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun insertDB() {
        var name = binding.etName.text.toString()
        var age = binding.etAge.text.toString()

        if (name.isNotEmpty() && age.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                var insertRoomModel = RoomModel(0, name, age)
                mainViewModel.insertDB(insertRoomModel)

                withContext(Dispatchers.Main){
                    Toast.makeText(App.context, "RoomDB insert완료", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(App.context, "Name, age 입력해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

}