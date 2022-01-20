package com.ceylonapz.weatherlive.view.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.databinding.ActivityFavoriteBinding
import com.ceylonapz.weatherlive.model.adapters.FavoriteLocationAdapter
import com.ceylonapz.weatherlive.utilities.db.Favorite
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
        val adapter =
            FavoriteLocationAdapter({ selectedLocation -> openEnterLocationDialog(selectedLocation) })
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
            openEnterLocationDialog(null)
        }
    }

    fun openEnterLocationDialog(favorite: Favorite?) {

        var isUpdateTask = false
        if (favorite != null) {
            isUpdateTask = true
        }

        val view = layoutInflater.inflate(R.layout.dialog_location_entry, null);
        val alertDialog = MaterialAlertDialogBuilder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(getMessage(isUpdateTask))

        val edit = view.findViewById<EditText>(R.id.editLocation)
        edit.setText(favorite?.locationName)

        alertDialog.setPositiveButton(getPositiveBtnName(isUpdateTask)) { dialog, which ->
            val newLocation = edit.text.toString()

            if (isUpdateTask) {
                favViewModel.updateLocation(favorite!!.copy(locationName = newLocation))
            } else {
                favViewModel.addNewLocation(newLocation)
            }

            dialog.dismiss()
        }

        if (isUpdateTask) {
            alertDialog.setNeutralButton(getString(R.string.delete)) { dialog, which ->
                favViewModel.deleteLocation(favorite!!)
                dialog.dismiss()
            }
        }

        alertDialog.setNegativeButton(getString(android.R.string.cancel), null)

        alertDialog.setView(view);
        alertDialog.show();
    }

    private fun getPositiveBtnName(isUpdateTask: Boolean): String {
        when (isUpdateTask) {
            true -> return getString(R.string.update)
            false -> return getString(R.string.save)
        }

    }

    private fun getMessage(isUpdateTask: Boolean): String {
        when (isUpdateTask) {
            true -> return getString(R.string.update_location_message)
            false -> return getString(R.string.enter_location_message)
        }
    }
}