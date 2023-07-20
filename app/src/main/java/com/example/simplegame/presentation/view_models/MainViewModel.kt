package com.example.simplegame.presentation.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    init {

    }

    private val _data = MutableLiveData<String>()
    val data: LiveData<String> get() = _data


    fun sendData(cmd: String){
        _data.value = cmd
    }
}