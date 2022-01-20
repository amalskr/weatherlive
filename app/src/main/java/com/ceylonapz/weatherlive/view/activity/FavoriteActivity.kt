package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivityFavoriteBinding
import com.ceylonapz.weatherlive.model.adapters.FavoriteLocationAdapter
import com.ceylonapz.weatherlive.viewmodel.FavoriteViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

        getDbUpdateStatus()
        setupFavList()
        clickActions()
    }

    fun getDbUpdateStatus() {
        favViewModel.isdbUpdated.observe(this) { updateMessage ->
            Toast.makeText(applicationContext, updateMessage, Toast.LENGTH_SHORT).show()
        }
    }

    fun setupFavList() {
        val adapter = FavoriteLocationAdapter()
        binding.recyclerFavLocation.adapter = adapter
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: FavoriteLocationAdapter) {
        favViewModel.allFavoriteLocations.observe(this) { favoriteLocations ->
            adapter.submitList(favoriteLocations)
        }
    }

    fun clickActions() {
        binding.fabAddLocation.setOnClickListener {
            openEnterLocationDialog()
        }
    }

    fun openEnterLocationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(getString(R.string.enter_location_message))
            .setView(R.layout.dialog_location_entry)
            .setPositiveButton(getString(R.string.save)) { dialog, which ->
                val newLocation =
                    (dialog as? AlertDialog)?.findViewById<EditText>(R.id.editLocation)?.text?.toString()
                favViewModel.addNewLocation(newLocation.toString())
                dialog.dismiss()
            }
            .setNegativeButton(getString(android.R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}