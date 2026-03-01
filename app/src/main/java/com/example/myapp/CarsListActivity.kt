package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapp.databinding.ActivityCarsListBinding
import com.example.myapp.model.Car

class CarsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCarsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding
        binding = ActivityCarsListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val carsList = listOf(
            Car(
                brand = "Ford",
                model = "Mustang GT500",
                year = 2020,
                description = "Мощный спортивный автомобиль с двигателем V8",
                cost = 7500000,
                imageResId = R.drawable.mustang
            ),
            Car(
                brand = "Mazerratti",
                model = "X",
                year = 2023,
                description = "Мощный спортивный автомобиль ",
                cost = 8900000,
                imageResId = R.drawable.mazeratti
            ),
            Car(
                brand = "Ford",
                model = "Mustang GT500",
                year = 2020,
                description = "Мощный спортивный автомобиль с двигателем V8",
                cost = 7500000,
                imageResId = R.drawable.mustang
            ),
            Car(
                brand = "Mazerratti",
                model = "X",
                year = 2023,
                description = "Мощный спортивный автомобиль ",
                cost = 8900000,
                imageResId = R.drawable.mazeratti
            ),
            Car(
                brand = "Ford",
                model = "Mustang GT500",
                year = 2020,
                description = "Мощный спортивный автомобиль с двигателем V8",
                cost = 7500000,
                imageResId = R.drawable.mustang
            ),
            Car(
                brand = "Mazerratti",
                model = "X",
                year = 2023,
                description = "Мощный спортивный автомобиль ",
                cost = 8900000,
                imageResId = R.drawable.mazeratti
            )
        )

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CarAdapter(carsList)
    }


    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}