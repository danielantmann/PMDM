package com.example.doslistaxml.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doslistaxml.domain.model.Tarea
import com.example.doslistaxml.domain.useCases.AddTareaUseCase
import com.example.doslistaxml.domain.useCases.CargarTareasUseCase
import com.example.doslistaxml.domain.useCases.DeleteTareaUseCase
import com.example.doslistaxml.ui.main.MainState

class MainViewModel(
    private val addTareaUseCase: AddTareaUseCase,
    private val deleteTareaUseCase: DeleteTareaUseCase,
    private val cargarTareasUseCase: CargarTareasUseCase
) : ViewModel() {

    private val _state = MutableLiveData(MainState())
    val state: LiveData<MainState> = _state

    init {
        handleCargarLista()
    }

    fun handleCargarLista() {
        val todas = cargarTareasUseCase.cargarTareas()
        _state.value = _state.value?.copy(
            tareasPendientes = todas.filter { !it.isTerminada },
            tareasCompletadas = todas.filter { it.isTerminada }
        )
    }

    fun handleSelectTarea(tarea: Tarea) {
        _state.value = _state.value?.copy(
            tareaSeleccionada = tarea,
            tareaForm = tarea, // Carga automáticamente los datos en el formulario
            error = null
        )
    }

    fun handleClearSelection() {
        _state.value = _state.value?.copy(
            tareaSeleccionada = null,
            tareaForm = Tarea()
        )
    }


    fun handleFormChange(nombre: String, descripcion: String) {
        val actual = _state.value?.tareaForm ?: Tarea()
        _state.value = _state.value?.copy(
            tareaForm = actual.copy(nombre = nombre, descripcion = descripcion),
            error = null
        )
    }

    fun handleGuardar() {
        val form = _state.value?.tareaForm ?: return
        if (form.nombre.isBlank()) {
            _state.value = _state.value?.copy(error = "El nombre es obligatorio")
            return
        }
        if (form.descripcion.isBlank()) {
            _state.value = _state.value?.copy(error = "La descripción es obligatoria")
            return
        }

        // Si el ID es 0 es nueva, si ya tiene ID hace update (reemplaza)
        val tareaFinal = if (form.id == 0L) {
            form.copy(id = System.currentTimeMillis(), isTerminada = false)
        } else {
            form
        }

        addTareaUseCase.addTarea(tareaFinal)
        handleCargarLista()

        _state.value = _state.value?.copy(
            tareaForm = Tarea(),
            tareaSeleccionada = null,
            mensaje = "¡Guardado con éxito!"
        )
    }

    fun handleMover(tarea: Tarea) {
        val tareaModificada = tarea.copy(isTerminada = !tarea.isTerminada)
        addTareaUseCase.addTarea(tareaModificada)
        handleCargarLista()

        _state.value = _state.value?.copy(
            tareaSeleccionada = null,
            tareaForm = Tarea(),
            mensaje = if (tareaModificada.isTerminada) "Movida a completadas" else "Devuelta a pendientes"
        )
    }

    fun handleDelete() {
        val seleccionada = _state.value?.tareaSeleccionada
        if (seleccionada == null) {
            _state.value = _state.value?.copy(error = "Selecciona una tarea primero")
            return
        }

        deleteTareaUseCase.deleteTarea(seleccionada)
        handleCargarLista()

        _state.value = _state.value?.copy(
            tareaSeleccionada = null,
            tareaForm = Tarea(),
            mensaje = "Tarea eliminada"
        )
    }

    class MainViewModelFactory(
        private val addTareaUseCase: AddTareaUseCase,
        private val deleteTareaUseCase: DeleteTareaUseCase,
        private val cargarTareasUseCase: CargarTareasUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(addTareaUseCase, deleteTareaUseCase, cargarTareasUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}