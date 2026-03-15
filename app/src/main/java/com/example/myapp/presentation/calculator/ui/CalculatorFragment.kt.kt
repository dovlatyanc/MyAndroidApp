package com.example.myapp.presentation.calculator.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.R
import com.example.myapp.databinding.FragmentCalculatorBinding
import com.example.myapp.presentation.calculator.utils.toUserMessage
import com.example.myapp.presentation.calculator.viewmodel.CalculatorViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private val viewModel: CalculatorViewModel by viewModel()

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCalculatorBinding.bind(view)

        setupObservers()
        setupClickListeners()
    }


    private fun setupObservers() {

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.calcResult.text = state.expression.ifEmpty {
                state.result
            }

            if (state.isError) {
                val errorMessage = state.errorCode?.toUserMessage(
                    context = requireContext()
                ) ?: getString(R.string.error_calc_unknown)

                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                viewModel.onErrorShown()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnZero.setOnClickListener { viewModel.onNumberClick(NUMBER_ZERO) }
        binding.btnOne.setOnClickListener { viewModel.onNumberClick(NUMBER_ONE) }
        binding.btnTwo.setOnClickListener { viewModel.onNumberClick(NUMBER_TWO) }
        binding.btnThree.setOnClickListener { viewModel.onNumberClick(NUMBER_THREE) }
        binding.btnFour.setOnClickListener { viewModel.onNumberClick(NUMBER_FOUR) }
        binding.btnFive.setOnClickListener { viewModel.onNumberClick(NUMBER_FIVE) }
        binding.btnSix.setOnClickListener { viewModel.onNumberClick(NUMBER_SIX) }
        binding.btnSeven.setOnClickListener { viewModel.onNumberClick(NUMBER_SEVEN) }
        binding.btnEight.setOnClickListener { viewModel.onNumberClick(NUMBER_EIGHT) }
        binding.btnNine.setOnClickListener { viewModel.onNumberClick(NUMBER_NINE) }

        binding.btnPlus.setOnClickListener { viewModel.onOperatorClick(OPERATOR_PLUS) }
        binding.btnMinus.setOnClickListener { viewModel.onOperatorClick(OPERATOR_MINUS) }
        binding.btnMulty.setOnClickListener { viewModel.onOperatorClick(OPERATOR_MULTIPLY) }
        binding.btnDivide.setOnClickListener { viewModel.onOperatorClick(OPERATOR_DIVIDE) }

        binding.btnReverse.setOnClickListener { viewModel.onReverseClick() }
        binding.btnPercent.setOnClickListener { viewModel.onPercentClick() }
        binding.btnDot.setOnClickListener { viewModel.onDotClick() }

        binding.btnClear.setOnClickListener { viewModel.onBackspaceClick() }
        binding.btnAC.setOnClickListener { viewModel.onClearClick() }
        binding.btnEquals.setOnClickListener { viewModel.onEqualsClick() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val NUMBER_ZERO = "0"
        private const val NUMBER_ONE = "1"
        private const val NUMBER_TWO = "2"
        private const val NUMBER_THREE = "3"
        private const val NUMBER_FOUR = "4"
        private const val NUMBER_FIVE = "5"
        private const val NUMBER_SIX = "6"
        private const val NUMBER_SEVEN = "7"
        private const val NUMBER_EIGHT = "8"
        private const val NUMBER_NINE = "9"

        private const val OPERATOR_PLUS = "+"
        private const val OPERATOR_MINUS = "-"
        private const val OPERATOR_MULTIPLY = "*"
        private const val OPERATOR_DIVIDE = "/"
    }
}