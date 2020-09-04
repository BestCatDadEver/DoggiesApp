package com.example.dogs.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DogDao {
    @Insert
    suspend fun insertAll(vararg dogs : Dog) : List<Long>

    @Query("SELECT * FROM dog")
    suspend fun getAll() : List<Dog>

    @Query("SELECT * FROM dog WHERE uuid = :dogId")
    suspend fun  getDogById(dogId : Int) : Dog

    @Query("DELETE FROM dog")
    suspend fun deleteAllDogs()

}