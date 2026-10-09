package com.example.calcularaprobados.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calcularaprobados.R
import com.example.calcularaprobados.databinding.ActivityMainBinding
import com.example.calcularaprobados.di.AppModule

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            AppModule.calcularAprobadosUseCases,
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

        setUpEvento()
    }

    private fun setUpEvento(){
        binding.buttonCalcular.setOnClickListener {
            val nota = binding.etNota.text.toString()

            viewModel.handleCalcularNotas(nota)
        }

        binding.buttonCalcularPromedio.setOnClickListener {
            viewModel.handleCalcularPromedio()
        }
    }

    private fun observarEstado(){
        viewModel.state.observe(this,{
            it?.resultado.let {
                binding.resultadoTexto.text = it
            }

            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.state.observe(this,{
            it?.promedio.let {
                binding.promedioTexto.text = it
            }

            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            }
        })



    }
}