package com.ceylonapz.weatherlive.model.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ceylonapz.weatherlive.R
import com.ceylonapz.weatherlive.model.Days

class ForecastDayAdapter(private val dayList: List<Days>) :
    RecyclerView.Adapter<ForecastDayAdapter.ViewHolder>() {

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val txtTitle: TextView = view.findViewById(R.id.txtForecastDate)
        val imageView: ImageView = view.findViewById(R.id.txtForecastImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.day_list_item, parent, false)
        return ViewHolder(viewLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastDay = dayList[position]
        holder.txtTitle.text = forecastDay.datetime
        //holder.imageView.setImageResource(myItem.imageResourceId)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}