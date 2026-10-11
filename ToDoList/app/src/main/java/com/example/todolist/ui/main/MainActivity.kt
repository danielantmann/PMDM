package com.example.todolist.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.example.todolist.R
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.di.AppModule

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private  lateinit var  adaptador: TareaListAdapter

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            AppModule.guardarTareaUseCase,
            AppModule.eliminarTareaUseCase,
            AppModule.obtenerTareasUseCase,
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

        setUpRecyclerView()
        observarEstado()
        setUpEventos()
    }

    private fun setUpRecyclerView(){
        adaptador = TareaListAdapter(emptyList()){idTarea ->
            viewModel.handleEliminarTarea(idTarea)
        }
        binding.recyclerViewTarea.adapter = adaptador
    }

    private fun setUpEventos(){
        binding.buttonAgregar.setOnClickListener {
            val tarea = binding.etTarea.text.toString()

            viewModel.handleGuardarTarea(tarea)
        }
    }

    private fun observarEstado(){
        viewModel.state.observe(this,{
            it?.listaTarea?.let { lista ->
                // Si prefieres recrear el adaptador (válido para examen rápido):
                adaptador = TareaListAdapter(lista) { idRegistro ->
                    viewModel.handleEliminarTarea(idRegistro)
                }
                binding.recyclerViewTarea.adapter =adaptador
            }

            it?.error?.let{ error ->
                Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
            }

        })
    }
}