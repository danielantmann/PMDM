package com.example.doslistastres.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.doslistastres.domain.model.Tarea
import com.example.doslistastres.domain.useCases.AddTareaUseCase
import com.example.doslistastres.domain.useCases.CargarTareasUseCase
import com.example.doslistastres.domain.useCases.DeleteTareaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addTareaUseCase: AddTareaUseCase,
    private val deleteTareaUseCase: DeleteTareaUseCase,
    private val cargarTareasUseCase: CargarTareasUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            val todasLasTareas = cargarTareasUseCase.cargarTareas()
            _state.update {
                it.copy(
                    tareasPendientes = todasLasTareas.filter { tarea -> !tarea.isTerminada },
                    tareasCompletadas = todasLasTareas.filter { tarea -> tarea.isTerminada }
                )
            }
        }
    }

    fun onChangeTarea(tareaForm: Tarea){
        _state.update {
            it.copy(
                tarea = tareaForm,
                error = null,
                mensaje = null
            )
        }
    }

    fun onAddTarea() {
        viewModelScope.launch {
            _state.update { it.copy(error = null) }
            val tareaForm = _state.value.tarea

            if (tareaForm.nombre.isBlank()) {
                _state.update { it.copy(error = "El nombre es obligatorio", mensaje = null) }
                return@launch
            }
            if (tareaForm.descripcion.isBlank()) {
                _state.update { it.copy(error = "La descripción es obligatoria", mensaje = null) }
                return@launch
            }

            // Llamamos al caso de uso. Como tareaForm mantiene el ID original si se seleccionó,
            // el repositorio decidirá si actualiza o añade.
            addTareaUseCase.addTarea(tareaForm)
            cargarDatos()

            _state.update {
                it.copy(
                    tarea = Tarea(),          // Limpiamos el formulario
                    tareaSeleccionado = null, // Limpiamos la selección de la tarjeta
                    error = null,
                    mensaje = "¡Guardado con éxito!"
                )
            }
        }
    }

    fun onSelectTarea(tarea: Tarea) {
        _state.update {
            it.copy(
                tareaSeleccionado = tarea,
                error = null,
                mensaje = null
            )
        }
    }

    fun onDeleteTarea() {
        val seleccionado = _state.value.tareaSeleccionado
        if (seleccionado == null) {
            _state.update {
                it.copy(
                    error = "Selecciona una tarea primero",
                    mensaje = null
                )
            }
            return
        }

        viewModelScope.launch {
            deleteTareaUseCase.deleteTarea(seleccionado)
            cargarDatos()
            _state.update {
                it.copy(
                    tareaSeleccionado = null,
                    tarea = Tarea(),
                    error = null,
                    mensaje = "Tarea eliminada"
                )
            }
        }
    }

    // Mueve la tarea directamente al hacer clic en el botón de su tarjeta
    fun moverTareaDirecta(tarea: Tarea) {
        viewModelScope.launch {
            val tareaModificada = tarea.copy(isTerminada = !tarea.isTerminada)
            addTareaUseCase.addTarea(tareaModificada)
            cargarDatos()

            _state.update {
                it.copy(
                    tareaSeleccionado = null,
                    mensaje = if (tareaModificada.isTerminada) "Tarea movida a completadas" else "Tarea devuelta a pendientes"
                )
            }
        }
    }

    fun clearMessages() {
        _state.update { it.copy(error = null, mensaje = null) }
    }
}

class MainViewModelFactory(
    private val addTareaUseCase: AddTareaUseCase,
    private val deleteTareaUseCase: DeleteTareaUseCase,
    private val cargarTareasUseCase: CargarTareasUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addTareaUseCase, deleteTareaUseCase, cargarTareasUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}