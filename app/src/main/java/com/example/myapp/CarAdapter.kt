package com.example.myapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.ItemCarBinding
import com.example.myapp.model.Car

class CarAdapter(
    private val cars: List<Car>
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = ItemCarBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(cars[position])
    }

    override fun getItemCount(): Int = cars.size

    class CarViewHolder(
        private val binding: ItemCarBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(car: Car) {
            binding.textBrandModel.text = "${car.brand} ${car.model}"
            binding.textYear.text = car.year.toString()
            binding.textDescription.text = car.description

            binding.textPrice.text = "${car.cost.toLocaleString()} ₽"

            binding.imageCar.setImageResource(car.imageResId)
        }
    }
}


private fun Int.toLocaleString(): String {
    return this.toString().reversed().chunked(3).joinToString(" ").reversed()
}