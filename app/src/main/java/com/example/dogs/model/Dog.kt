package com.example.dogs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Dog(

    @ColumnInfo(name = "breed_id")
    @SerializedName("id")
    val dogId : String?,

    @ColumnInfo(name = "dog_name")
    @SerializedName("name")
    val dogBreed : String?,

    @ColumnInfo(name = "dog_span")
    @SerializedName("life_span")
    val lifeSpan :String?,

    @ColumnInfo(name = "breed_group")
    @SerializedName("breed_group")
    val breedGroup : String?,

    @ColumnInfo(name = "bred_for")
    @SerializedName("bred_for")
    val bredFor : String?,

    @SerializedName("temperament")
    val temperament : String?,

    @ColumnInfo(name = "dog_url")
    @SerializedName("url")
    val imageUrl : String?
){
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}

//This class has the color that will be assigned to each element. (DetailFragment)
data class DogPalette(var color : Int)


