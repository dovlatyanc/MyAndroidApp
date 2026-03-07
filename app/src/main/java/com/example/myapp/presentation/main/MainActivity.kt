package com.example.myapp.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.presentation.calculator.CalculatorActivity
import com.example.myapp.presentation.car.CarsListActivity
import com.example.myapp.presentation.weather.WeatherActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWeather.setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }

        binding.openCalc.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }

        binding.btnCarsList.setOnClickListener {
            startActivity(Intent(this, CarsListActivity::class.java))
        }
    }
}