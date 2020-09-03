package com.example.dogs.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.Dog
import com.example.dogs.model.DogsApiClient
import com.example.dogs.model.DogsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    val dogs = MutableLiveData<List<Dog>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val dogsService = DogsApiService()
    val disposable = CompositeDisposable()

    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(dogsService.getDogs()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object:DisposableSingleObserver<List<Dog>>(){
                override fun onSuccess(dogList: List<Dog>) {
                    dogs.value = dogList
                    dogsLoadError.value = false
                    loading.value = false
                }

                override fun onError(e: Throwable) {
                    dogsLoadError.value = true
                    loading.value = false
                    e.printStackTrace()
                }
            }))


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}