package com.example.formlistacompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.example.formlistacompose.domain.model.Alumno
import com.example.formlistacompose.domain.useCases.AddAlumnoUseCase
import com.example.formlistacompose.domain.useCases.CargarAlumnosUseCase
import com.example.formlistacompose.domain.useCases.DeleteAlumnoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private val cargarAlumnosUseCase: CargarAlumnosUseCase,
    private val deleteAlumnoUseCase: DeleteAlumnoUseCase
): ViewModel() {

    // 1. El estado interno mutable
    private val _state = MutableStateFlow(MainState())
    // 2. El estado público de sólo lectura que observará la pantalla
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarAlumnosUseCase.cargarAlumnos()
            _state.update { it.copy(alumnos = listaInicial) }
        }
    }

    // Actualiza es estado(el objeto alumno que es son los inputs) con el nuevo alumno
    fun onChangeAlumno(alumnoForm: Alumno){
        _state.update {
            it.copy(alumno = alumnoForm,
                    error = null)

        }
    }

    fun onAddAlumno(){
        viewModelScope.launch {
            _state.update { it.copy(error = null) }
            val nuevoAlumno = _state.value.alumno

            addAlumnoUseCase.addAlumno(nuevoAlumno)

            val listaActualizada = cargarAlumnosUseCase.cargarAlumnos()

            _state.update {
                it.copy(
                    alumnos = listaActualizada,
                    alumno = Alumno(
                        nombre = "",
                        apellido = "",
                        curso = "",
                        edad = "",
                        email = "",
                        isActive = true
                    ),
                )
            }

        }
    }

    fun onDeleteAlumno(alumno: Alumno){
        viewModelScope.launch {
            deleteAlumnoUseCase.deleteAlumno(alumno)

            val listaActualizada = cargarAlumnosUseCase.cargarAlumnos()
            _state.update { it.copy(alumnos = listaActualizada) }

        }
    }

    fun limpiarError() {
        _state.update { it.copy(error = null) }
    }
}

class MainViewModelFactory(
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private val cargarAlumnosUseCase: CargarAlumnosUseCase,
    private val deleteAlumnoUseCase: DeleteAlumnoUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.formlistacompose.ui.main.MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainViewModel(
                addAlumnoUseCase, cargarAlumnosUseCase, deleteAlumnoUseCase

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}