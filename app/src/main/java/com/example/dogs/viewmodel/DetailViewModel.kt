package com.example.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.Dog
import com.example.dogs.model.DogsApiService

class DetailViewModel : ViewModel() {

    val dogLiveData = MutableLiveData<Dog>()


    fun fetch(){

    }


}