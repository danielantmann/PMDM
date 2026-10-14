package com.example.conversorfarenheit.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.conversorfarenheit.R
import com.example.conversorfarenheit.databinding.ActivityMainBinding
import com.example.conversorfarenheit.di.AppModule

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            AppModule.convertirGradosUseCase,
            AppModule.gurdarConversionesUseCase,
            AppModule.obtenerConversionesUseCase
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
            val grados = binding.etGrados.text.toString()
            viewModel.handleConverirGrados(grados)
        }
    }

    private fun observarEstado(){
        viewModel.state.observe(this, {
            it?.resultado?.let { it ->
                binding.resultado.text = "${it}"
            }
            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            }
        })
    }
}