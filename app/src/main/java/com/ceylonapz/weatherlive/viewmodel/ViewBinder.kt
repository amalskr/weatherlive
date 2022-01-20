package com.ceylonapz.weatherlive.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ceylonapz.weatherlive.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Glide.with(view.context)
            .load("http://openweathermap.org/img/wn/02d@2x.png")
            .placeholder(R.drawable.ic_forecast_fair_day)
            .error(R.drawable.ic_launcher_foreground)
            .into(view)
    }
}

@BindingAdapter("isFabGone")
fun bindIsFabGone(view: FloatingActionButton, isGone: Boolean?) {
    if (isGone == null || isGone) {
        view.hide()
    } else {
        view.show()
    }
}
