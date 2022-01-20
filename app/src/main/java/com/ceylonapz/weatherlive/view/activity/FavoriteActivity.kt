package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.databinding.ActivityFavoriteBinding
import com.ceylonapz.weatherlive.viewmodel.FavoriteViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val favViewModel: FavoriteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.viewModel = favViewModel

        binding.btnAdd.setOnClickListener { favViewModel.addNewLocation("Colombo") }
        favViewModel.allFavoriteLocations.observe(this) { favoriteLocations ->
            println("myFavList " + favoriteLocations.size)
        }
    }
}