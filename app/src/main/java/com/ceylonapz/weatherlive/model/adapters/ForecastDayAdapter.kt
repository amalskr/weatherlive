package com.ceylonapz.weatherlive.model.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ceylonapz.weatherlive.databinding.DayListItemBinding
import com.ceylonapz.weatherlive.model.Days
import com.ceylonapz.weatherlive.viewmodel.DayItemViewModel

class ForecastDayAdapter(
    private val tempType: String,
    private val dayList: List<Days>,
    private val listener: (Days) -> Unit
) :
    RecyclerView.Adapter<ForecastDayAdapter.ViewHolder>() {

    class ViewHolder(private val binding: DayListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Days, tempType: String) {
            binding.apply {
                dayModel = DayItemViewModel(item, tempType)
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
        holder.bind(forecastDay, tempType)
        holder.itemView.setOnClickListener { listener(forecastDay) }

    }

    override fun getItemCount(): Int {
        return dayList.size
    }

}