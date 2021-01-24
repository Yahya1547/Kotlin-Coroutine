package com.example.threading.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val taskNameLiveData: MutableLiveData<String> by lazy { MutableLiveData() }
    val progressLiveData: MutableLiveData<Int> by lazy { MutableLiveData() }

    fun getTaskName(): LiveData<String> = taskNameLiveData

    fun getProgress(): LiveData<Int> = progressLiveData
}