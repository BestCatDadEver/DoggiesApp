package com.example.dogs.model

import io.reactivex.Single
import retrofit2.http.GET

interface DogsApiClient {
    @GET("DevTides/DogsApi/master/dogs.json")
    fun getDogs() : Single<List<Dog>>
}