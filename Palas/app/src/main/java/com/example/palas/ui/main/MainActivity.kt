package com.example.palas.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.palas.R
import com.example.palas.databinding.ActivityMainBinding
import com.example.palas.di.AppModule
import com.example.palas.domain.model.Pala


class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels { MainViewModel.MainViewModelFactory(
        AppModule.damePalaUseCase,
        AppModule.cargarPalasUseCase,
        AppModule.addPalaUseCase,
        AppModule.deletePalaUseCase,

    ) }

    private lateinit var adapter: PalaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        viewModel.handleCargarLista()

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        configRecycler()
        observarEstado()
        setUpEventos()


    }

    private fun configRecycler() {
        binding.listaPalas.layoutManager = LinearLayoutManager(this)

        adapter = PalaAdapter({ pala ->
            viewModel.handleDelete(pala)
        })
        binding.listaPalas.adapter = adapter
    }

    private fun setUpEventos(){
        with(binding){
            buttonDamePala.setOnClickListener {
                viewModel.handleDameJugador()
                viewModel.handleCargarLista()
            }

            buttonAddPala.setOnClickListener{
                var p = Pala(nombre= "ML27", marca = "nox", peso = 320)
                viewModel.handleAdd(p)
            }
        }

    }

    private fun observarEstado(){
        viewModel.state.observe(this,{
            it?.pala.let {
                binding.textView.text = it
            }

            it?.palas.let{
                adapter.submitList(it)
            }
            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()

            }
        })
    }
}