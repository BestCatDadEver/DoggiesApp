package com.example.dogs.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogs.model.Dog
import com.example.dogs.model.DogDatabase
import com.example.dogs.model.DogsApiClient
import com.example.dogs.model.DogsApiService
import com.example.dogs.utl.NotificationsHelper
import com.example.dogs.utl.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val prefHelper = SharedPreferencesHelper(getApplication())
    private val dogsService = DogsApiService()
    private val disposable = CompositeDisposable()
    private var refreshTime = 5 * 60 * 1000 * 1000 * 1000L
    val dogs = MutableLiveData<List<Dog>>()
    val dogsLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        val updateTime = prefHelper.getUpdateTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            fetchFromDatabase()
        }else{
            fetchFromRemote()
        }
    }

    fun refreshBypassCache(){
        fetchFromRemote()
    }

    private  fun fetchFromDatabase(){
        loading.value = true
        launch {
            val dogs = DogDatabase(getApplication()).dogDao().getAll()
            dogsRetrieved(dogs)
            Toast.makeText(getApplication(), "Dogs retrieved from Database", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(dogsService.getDogs()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object:DisposableSingleObserver<List<Dog>>(){
                override fun onSuccess(dogList: List<Dog>) {
                    storeDogsLocally(dogList)
                    Toast.makeText(getApplication(), "Dogs retrieved from Endpoint", Toast.LENGTH_SHORT).show()
                    NotificationsHelper(getApplication()).createNotification()
                }

                override fun onError(e: Throwable) {
                    dogsLoadError.value = true
                    loading.value = false
                    e.printStackTrace()
                }
            }))
    }

    private fun dogsRetrieved(dogsList : List<Dog>){
        dogs.value = dogsList
        dogsLoadError.value = false
        loading.value = false
    }

    private fun storeDogsLocally(list : List<Dog>){
        launch {
            val dao = DogDatabase(getApplication()).dogDao()
            //Delete all dogs so the second time this is used there is no redundant information.
            dao.deleteAllDogs()
            //*ilst expands the list to individual elements to pass to our insertAll function.
            val result = dao.insertAll(*list.toTypedArray())
            //assign uuids to dog object
            var i = 0
            while(i < list.size){
                list[i].uuid = result[i].toInt()
                ++i
            }
            dogsRetrieved(list)
        }
        prefHelper.saveUpdateTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}