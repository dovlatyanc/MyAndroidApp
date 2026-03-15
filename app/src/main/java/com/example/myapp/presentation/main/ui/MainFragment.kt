package com.example.myapp.presentation.main.ui


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        binding.btnWeather.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_weather)
        }

        binding.openCalc.setOnClickListener {
            findNavController().navigate(R.id.action_main_to_calculator)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}