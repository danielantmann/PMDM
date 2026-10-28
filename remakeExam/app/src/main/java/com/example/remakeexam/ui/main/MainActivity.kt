package com.example.remakeexam.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
// Cambia por tu package real
import com.example.remakeexam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 1. Declarar el Binding
    private lateinit var binding: ActivityMainBinding

    // 2. Instanciar el ViewModel
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeState()
    }

    private fun setupListeners() {
        // Evento botón "Todas"
        binding.btnTodas.setOnClickListener {
            viewModel.toggleAllTasks()
        }

        // Eventos barra inferior (los 4 TextViews que pusimos abajo)
        binding.navInicio.setOnClickListener { viewModel.onBottomNavClicked("Inicio") }
        binding.navTareas.setOnClickListener { viewModel.onBottomNavClicked("Tareas") }
        binding.navEstadisticas.setOnClickListener { viewModel.onBottomNavClicked("Estadísticas") }
        binding.navPerfil.setOnClickListener { viewModel.onBottomNavClicked("Perfil") }
    }

    private fun observeState() {
        // Observar los cambios en el State del ViewModel
        viewModel.state.observe(this) { state ->

            // 1. Marcar / desmarcar los 4 checkboxes según el botón "Todas"
            binding.cbTask1.isChecked = state.areAllChecked
            binding.cbTask2.isChecked = state.areAllChecked
            binding.cbTask3.isChecked = state.areAllChecked
            binding.cbTask4.isChecked = state.areAllChecked

            // 2. Actualizar el texto de las 4 tareas con el nombre del botón + las veces que se hizo clic
            // Ejemplo: "Inicio (3)"
            val textoDinamico = "${state.selectedNavButon} (${state.clickCount})"

            binding.tvTask1Title.text = textoDinamico
            binding.tvTask2Title.text = textoDinamico
            binding.tvTask3Title.text = textoDinamico
            binding.tvTask4Title.text = textoDinamico
        }
    }
}