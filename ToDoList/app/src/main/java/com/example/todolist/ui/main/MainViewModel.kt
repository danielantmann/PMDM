package com.example.todolist.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.domain.model.Tarea
import com.example.todolist.domain.useCases.EliminarTareaUseCase
import com.example.todolist.domain.useCases.GuardarTareaUseCase
import com.example.todolist.domain.useCases.ObtenerTareasUseCase

class MainViewModel(
    private val guardarTareaUseCase: GuardarTareaUseCase,
    private val eliminarTareaUseCase: EliminarTareaUseCase,
    private val obtenerTareasUseCase: ObtenerTareasUseCase
): ViewModel() {

    private val _state = MutableLiveData(MainState(listaTarea = emptyList()))
    val state: MutableLiveData<MainState> = _state

    init {
        // Carga inicial de tareas al arrancar el ViewModel
        _state.value = _state.value?.copy(
            listaTarea = obtenerTareasUseCase.obtenerTarea()
        )
    }

    fun handleGuardarTarea(tarea: String){
        if (tarea.isNullOrBlank()) {
            _state.value = _state.value?.copy(error = "Introduce una tarea")
            return
        }

        val tareaGuardar = Tarea(
            tarea = tarea
        )
        guardarTareaUseCase.guardarTarea(tareaGuardar)

        _state.value = _state.value?.copy(
            error = null,
            listaTarea = obtenerTareasUseCase.obtenerTarea()
        )
    }

    fun handleEliminarTarea(id: String){
        eliminarTareaUseCase.eliminarTarea(id)

        _state.value = _state.value?.copy(
            listaTarea = obtenerTareasUseCase.obtenerTarea()
        )
    }

    class MainViewModelFactory(
        private val guardarTareaUseCase: GuardarTareaUseCase,
        private val eliminarTareaUseCase: EliminarTareaUseCase,
        private val obtenerTareasUseCase: ObtenerTareasUseCase,

    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.todolist.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    guardarTareaUseCase,
                    eliminarTareaUseCase,
                    obtenerTareasUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}