package com.example.myapp.presentation.calculator.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.databinding.ActivityCalculatorBinding
import com.example.myapp.data.calculator.repository.CalculatorRepositoryImpl
import com.example.myapp.data.calculator.service.ExpressionCalculator
import com.example.myapp.domain.calculator.usecase.CalculateExpressionUseCase
import com.example.myapp.domain.calculator.usecase.ValidateExpressionUseCase
import com.example.myapp.presentation.calculator.viewmodel.CalculatorViewModel
import com.example.myapp.presentation.calculator.viewmodel.CalculatorViewModelFactory

class CalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatorBinding
    private lateinit var viewModel: CalculatorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupObservers()
        setupClickListeners()
    }


    private fun setupViewModel() {
        val calculator = ExpressionCalculator()
        val repository = CalculatorRepositoryImpl(calculator)
        val calculateUseCase = CalculateExpressionUseCase(repository)
        val validateUseCase = ValidateExpressionUseCase(repository)

        viewModel = ViewModelProvider(
            this,
            CalculatorViewModelFactory(calculateUseCase, validateUseCase)
        )[CalculatorViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.state.observe(this) { state ->
            binding.calcResult.text = state.expression.ifEmpty {
                state.result
            }

            if (state.isError) {
                Toast.makeText(this, state.errorMessage ?: "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnZero.setOnClickListener { viewModel.onNumberClick("0") }
        binding.btnOne.setOnClickListener { viewModel.onNumberClick("1") }
        binding.btnTwo.setOnClickListener { viewModel.onNumberClick("2") }
        binding.btnThree.setOnClickListener { viewModel.onNumberClick("3") }
        binding.btnFour.setOnClickListener { viewModel.onNumberClick("4") }
        binding.btnFive.setOnClickListener { viewModel.onNumberClick("5") }
        binding.btnSix.setOnClickListener { viewModel.onNumberClick("6") }
        binding.btnSeven.setOnClickListener { viewModel.onNumberClick("7") }
        binding.btnEight.setOnClickListener { viewModel.onNumberClick("8") }
        binding.btnNine.setOnClickListener { viewModel.onNumberClick("9") }

        binding.btnPlus.setOnClickListener { viewModel.onOperatorClick("+") }
        binding.btnMinus.setOnClickListener { viewModel.onOperatorClick("-") }
        binding.btnMulty.setOnClickListener { viewModel.onOperatorClick("*") }
        binding.btnDivide.setOnClickListener { viewModel.onOperatorClick("/") }

        binding.btnReverse.setOnClickListener { viewModel.onReverseClick() }
        binding.btnPercent.setOnClickListener { viewModel.onPercentClick() }
        binding.btnDot.setOnClickListener { viewModel.onDotClick() }

        binding.btnClear.setOnClickListener { viewModel.onBackspaceClick() }
        binding.btnAC.setOnClickListener { viewModel.onClearClick() }
        binding.btnEquals.setOnClickListener { viewModel.onEqualsClick() }
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}