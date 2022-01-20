package com.ceylonapz.weatherlive.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceylonapz.weatherlive.databinding.DayListItemBinding
import com.ceylonapz.weatherlive.model.Days

class ForecastDayAdapter(private val dayList: List<Days>) :
    RecyclerView.Adapter<ForecastDayAdapter.ViewHolder>() {

    class ViewHolder(private val binding: DayListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Days) {
            binding.apply {
                dayModel = item
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DayListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val forecastDay = dayList[position]
        holder.bind(forecastDay)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }
}