package com.example.jugadores.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jugadores.R
import com.example.jugadores.databinding.ActivityMainBinding
import com.example.jugadores.di.AppModule
import com.example.jugadores.domain.model.Jugador

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels { MainViewModel.MainViewModelFactory(
        AppModule.dameJugadorUseCase,
        AppModule.cargarJugadoresUseCase,
        AppModule.addJugadorUseCase,
        AppModule.delJugadorUseCase,
    ) }

    private lateinit var adapter: JugadorAdapter

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
        binding.listaJugadores.layoutManager = LinearLayoutManager(this)

        adapter = JugadorAdapter({jugador ->
            viewModel.handleDelete(jugador)
        })
        binding.listaJugadores.adapter = adapter
    }

    private fun setUpEventos(){
        with(binding){
            buttonDameJugador.setOnClickListener {
                viewModel.handleDameJugador()
                viewModel.handleCargarLista()
            }

            buttonAddJugador.setOnClickListener{
                var j = Jugador(nombre= "Riquelme", club = "Boca", goles = 320)
                viewModel.handleAdd(j)
            }
        }

    }

    private fun observarEstado(){
        viewModel.state.observe(this,{
            it?.jugador.let {
                binding.textView.text = it
            }

            it?.jugadores.let{
                adapter.submitList(it)
            }
            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()

            }
        })
    }
}