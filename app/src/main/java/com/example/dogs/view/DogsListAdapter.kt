package com.example.dogs.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.dogs.R
import com.example.dogs.databinding.ItemDogBinding
import com.example.dogs.model.Dog
import com.example.dogs.utl.getProgressDrawable
import com.example.dogs.utl.loadImage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogsList: ArrayList<Dog>) : RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(), DogClickListener{

    fun updateDogsList(newDogsList : List<Dog>){
        dogsList.clear()
        dogsList.addAll(newDogsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent,false)
        return DogViewHolder(view)
    }

    override fun getItemCount() = dogsList.size

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.view.dog = dogsList[position]
        holder.view.listener = this


        holder.view.dogItemImage.loadImage(dogsList[position].imageUrl, getProgressDrawable(holder.view.dogItemImage.context))
    }


    //retrieves an item "ItemDogBinding"
    class DogViewHolder(var view : ItemDogBinding) : RecyclerView.ViewHolder(view.root)

    override fun onDogClick(view: View) {
        //We get the uuid from the view
        val uuid = view.dogId.text.toString().toInt()
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        action.dogUuid  = uuid
        Navigation.findNavController(view).navigate(action)
    }

}