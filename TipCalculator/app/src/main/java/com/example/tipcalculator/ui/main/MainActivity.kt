package com.example.tipcalculator.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipcalculator.R
import com.example.tipcalculator.databinding.ActivityMainBinding
import com.example.tipcalculator.di.AppModule

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            AppModule.calcularPropinaUseCase,
            AppModule.guardarPropinasUseCase,
            AppModule.obtenerListaPropinasUseCase
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observarEstado()
        setUpEventos()
    }

    private fun setUpEventos(){
        binding.buttonCalcular.setOnClickListener {
            val total = binding.etBill.text.toString()
            val porcentaje = binding.etPercentage.text.toString()
            viewModel.handleCalcularPropina(total, porcentaje)
        }
    }

    private fun observarEstado(){
        viewModel.state.observe(this,{
            it?.resultado?.let {
                binding.total.text = " ${it}"
            }
            it?.totalGuardados?.let {
                binding.savedCalc.text = "Saved calculations : ${it}"
            }
            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            }
        })
    }
}