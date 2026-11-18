package com.example.doslistaxml.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.doslistaxml.R
import com.example.doslistaxml.databinding.ActivityMainBinding
import com.example.doslistaxml.di.AppModule

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory(
            AppModule.addTareaUseCase,
            AppModule.deleteTareaUseCase,
            AppModule.cargarTareasUseCase
        )
    }

    // Adaptador para Tareas Pendientes (con TareaAdapter2 en ambos lados)
    private val adapterPendientes: TareaAdapter2 by lazy {
        TareaAdapter2(
            onSelectClick = { tarea -> viewModel.handleSelectTarea(tarea) },
            onMoverClick = { tarea -> viewModel.handleMover(tarea) }
        )
    }

    // Adaptador para Tareas Completadas (con TareaAdapter2 en ambos lados)
    private val adapterCompletadas: TareaAdapter2 by lazy {
        TareaAdapter2(
            onSelectClick = { tarea -> viewModel.handleSelectTarea(tarea) },
            onMoverClick = { tarea -> viewModel.handleMover(tarea) }
        )
    }

    // Bandera para evitar el bucle infinito entre el EditText y el ViewModel
    private var isUpdatingFromViewModel = false

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
        binding.recyclerPendientes.layoutManager = LinearLayoutManager(this)
        binding.recyclerPendientes.adapter = adapterPendientes

        binding.recyclerCompletadas.layoutManager = LinearLayoutManager(this)
        binding.recyclerCompletadas.adapter = adapterCompletadas
    }

    private fun setupEventos() {
        with(binding) {
            // Clic en el fondo general para deseleccionar
            root.setOnClickListener {
                viewModel.handleClearSelection()
                etNombre.setText("")
                etDescripcion.setText("")
                etNombre.clearFocus()
                etDescripcion.clearFocus()
            }

            // Uso seguro con toString() y el operador nulo de Kotlin (?.)
            etNombre.addTextChangedListener {
                if (!isUpdatingFromViewModel) {
                    viewModel.handleFormChange(
                        it?.toString() ?: "",
                        etDescripcion.text?.toString() ?: ""
                    )
                }
            }

            etDescripcion.addTextChangedListener {
                if (!isUpdatingFromViewModel) {
                    viewModel.handleFormChange(
                        etNombre.text?.toString() ?: "",
                        it?.toString() ?: ""
                    )
                }
            }

            // El botón extra llama directamente al ViewModel para mover la seleccionada
            btnAccionExtra.setOnClickListener {
                viewModel.handleMoverSeleccionada()
                etNombre.setText("")
                etDescripcion.setText("")
            }

            btnGuardar.setOnClickListener {
                viewModel.handleGuardar()
                etNombre.setText("")
                etDescripcion.setText("")
            }

            btnBorrar.setOnClickListener {
                viewModel.handleDelete()
                etNombre.setText("")
                etDescripcion.setText("")
            }
        }
    }

    private fun observarEstado() {
        viewModel.state.observe(this) { state ->
            adapterPendientes.submitList(state.tareasPendientes.toList())
            adapterCompletadas.submitList(state.tareasCompletadas.toList())

            adapterPendientes.idSeleccionada = state.tareaSeleccionada?.id
            adapterCompletadas.idSeleccionada = state.tareaSeleccionada?.id

            // Activamos la bandera antes de actualizar los textos para evitar el bucle
            isUpdatingFromViewModel = true
            state.tareaForm.let { form ->
                if (binding.etNombre.text.toString() != form.nombre) {
                    binding.etNombre.setText(form.nombre)
                }
                if (binding.etDescripcion.text.toString() != form.descripcion) {
                    binding.etDescripcion.setText(form.descripcion)
                }
            }
            isUpdatingFromViewModel = false

            // Mostrar u ocultar el panel de detalle
            if (state.tareaSeleccionada != null) {
                binding.cardDetalle.visibility = View.VISIBLE
                binding.tvDetalleNombre.text = "Nombre: ${state.tareaSeleccionada.nombre}"
                binding.tvDetalleDescripcion.text = "Descripción: ${state.tareaSeleccionada.descripcion}"
            } else {
                binding.cardDetalle.visibility = View.GONE
            }

            state.error?.let { error ->
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
            state.mensaje?.let { mensaje ->
                Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
            }
        }
    }
}