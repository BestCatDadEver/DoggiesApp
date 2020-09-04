package com.example.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogs.R
import com.example.dogs.model.Dog
import com.example.dogs.utl.getProgressDrawable
import com.example.dogs.utl.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<Dog>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(){

    fun updateDogsList(newDogsList : List<Dog>){
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.name.text = dogsList[position].dogBreed
        holder.view.lifespan.text = dogsList[position].lifeSpan
        holder.view.setOnClickListener{
            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
            action.dogUuid = dogsList[position].uuid
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.dogItemImage.loadImage(dogsList[position].imageUrl, getProgressDrawable(holder.view.dogItemImage.context))
    }


    class DogViewHolder(var view : View) : RecyclerView.ViewHolder(view)

}