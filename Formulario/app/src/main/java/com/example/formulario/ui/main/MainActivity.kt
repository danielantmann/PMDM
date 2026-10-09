package com.example.formulario.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.formulario.R
import com.example.formulario.databinding.ActivityMainBinding
import com.example.formulario.di.AppModule

class MainActivity : AppCompatActivity() {

    private  val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            AppModule.dameUltimoUsuarioUseCase,
            AppModule.agregarUsuarioUseCase
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
        binding.Mandar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val mensaje = binding.etMensaje.text.toString()

            viewModel.handleAgregarUsuario(nombre,mensaje,)
        }

        binding.UltimoUsuario.setOnClickListener {
            viewModel.handleDameUltimoUsuario()
        }

    }

    private fun observarEstado(){
        viewModel.state.observe(this,{
            it?.textoMostrado.let {
                binding.tvResultado.text = it
            }

            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            }

        })
    }

}