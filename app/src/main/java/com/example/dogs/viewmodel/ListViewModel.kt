package com.example.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.Dog

class ListViewModel : ViewModel() {

    val dogs = MutableLiveData<List<Dog>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val dog1 = Dog("1","Corgy","13","breedGroup","bredFor","temperament","")
        val dog2 = Dog("2","Dogo","13","breedGroup","bredFor","temperament","")
        val dog3 = Dog("3","Cocker","13","breedGroup","bredFor","temperament","")
        val dog4 = Dog("4","Labrador","13","breedGroup","bredFor","temperament","")

        val dogList = arrayListOf(dog1,dog2,dog3,dog4)

        dogs.value = dogList
        dogsLoadError.value = true
        loading.value = false
    }
}