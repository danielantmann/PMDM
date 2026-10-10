package com.example.calcularipc.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.calcularipc.R
import com.example.calcularipc.databinding.ActivityMainBinding
import com.example.calcularipc.di.AppModule

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            AppModule.calcularImcUseCase,
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
            val nombre = binding.etNombre.text.toString()
            val peso = binding.etPeso.text.toString()
            val altura = binding.etAltura.text.toString()

            viewModel.handleCalcularIpc(nombre,peso,altura)
        }
    }

    private fun observarEstado(){
        viewModel.state.observe(this,{
            it?.resultadoImc.let {
                binding.textoResultado.text = it
            }

            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            }
        })
    }
}