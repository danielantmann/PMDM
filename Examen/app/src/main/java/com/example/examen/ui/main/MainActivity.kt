package com.example.examen.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.R
import com.example.examen.databinding.ActivityMainBinding
import com.example.examen.di.AppModule
import com.example.examen.domain.model.Alumno
import kotlin.getValue

class MainActivity : AppCompatActivity() {

    private  val binding : ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    private val adapter: AlumnoAdapter by lazy {
        AlumnoAdapter { alumnoBorrar->
            viewModel.handleDelete()
        }
    }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            AppModule.addAlumnoUseCase,
            AppModule.eliminarAlumnoUseCase,
            AppModule.cargarListaAlumnosUseCase
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

        setupRecyclerView()
        observarEstado()
        setupEventos()
    }

    private fun setupRecyclerView() {
        binding.listado.adapter = adapter
        binding.listado.layoutManager = LinearLayoutManager(this)
    }

    private fun setupEventos(){
        with(binding){
            buttonAdd.setOnClickListener {
                val nombre = binding.etTarea.text.toString()
                val apellido = binding.etApellido.text.toString()
                val curso = binding.etCurso.text.toString()
                val edad = binding.etEdad.text.toString()
                val correo = binding.etMail.text.toString()
                val a = Alumno(nombre, apellido, curso,edad,correo)
                viewModel.handleAdd(a)

            }

            buttonEliminar.setOnClickListener {
                viewModel.handleDelete()
            }
        }

    }

    private fun observarEstado(){
        viewModel.state.observe(this){


            it?.listaAlumnos?.let {
                alumnos ->
                adapter.submitList(alumnos.toList())

            }

            it?.error?.let { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()

            }

        }
    }
}