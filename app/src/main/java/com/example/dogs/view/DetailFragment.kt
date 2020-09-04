package com.example.dogs.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.dogs.R
import com.example.dogs.utl.getProgressDrawable
import com.example.dogs.utl.loadImage
import com.example.dogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private var dogUuid = 0
    private lateinit var viewModel : DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUuid
        }
        viewModel.fetch(dogUuid)

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.dogLiveData.observe(this, Observer {dog ->
            dog?.let {
                dogName.text = dog.dogBreed
                dogLifespan.text = dog.lifeSpan
                dogPurpose.text = dog.bredFor
                dogTemperament.text = dog.temperament
                context?.let {
                       dogImage.loadImage(dog.imageUrl, getProgressDrawable(it))
                }
            }
        })
    }
}