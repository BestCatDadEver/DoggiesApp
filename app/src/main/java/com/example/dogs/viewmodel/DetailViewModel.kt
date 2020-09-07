package com.example.dogs.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.dogs.model.Dog
import com.example.dogs.model.DogDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : BaseViewModel(application) {

    val dogLiveData = MutableLiveData<Dog>()

    //Get dog information from DB using uuid, populate the dog and populate the screen with the correct information.
    fun fetch(uuid: Int){
        launch{
            val dog = DogDatabase(getApplication()).dogDao().getDogById(uuid)
            dogLiveData.value = dog
        }
    }



}