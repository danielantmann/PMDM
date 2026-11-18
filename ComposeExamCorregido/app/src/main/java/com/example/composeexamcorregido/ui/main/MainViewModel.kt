package com.example.composeexamcorregido.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.composeexamcorregido.domain.model.Alumno
import com.example.composeexamcorregido.domain.useCases.AddAlumnoUseCase
import com.example.composeexamcorregido.domain.useCases.CargarAlumnosUseCase
import com.example.composeexamcorregido.domain.useCases.DeleteAlumnoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private val deleteAlumnoUseCase: DeleteAlumnoUseCase,
    private val cargarAlumnosUseCase: CargarAlumnosUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarAlumnosUseCase.cargarAlumnos()
            _state.update { it.copy(alumnos = listaInicial) }
        }
    }

    fun onChangeAlumno(alumnoForm: Alumno){
        _state.update {
            it.copy(
                alumno = alumnoForm,
                error = null,
                mensaje = null
            )
        }
    }

    fun onAddAlumno(){
        viewModelScope.launch {
            _state.update { it.copy(error = null) }
            val nuevoAlumno = _state.value.alumno

            if (nuevoAlumno.nombre.isBlank()) {
                _state.update { it.copy(error = "El nombre es obligatorio", mensaje = null) }
                return@launch
            }
            if (nuevoAlumno.email.isBlank()) {
                _state.update { it.copy(error = "El email es obligatorio", mensaje = null) }
                return@launch
            }

            addAlumnoUseCase.addAlumno(nuevoAlumno)

            val listaActualizada = cargarAlumnosUseCase.cargarAlumnos()

            _state.update {
                it.copy(
                    alumnos = listaActualizada,
                    alumno = Alumno(),
                    error = null,
                    mensaje = "Alumno añadido"
                )
            }
        }
    }

    fun onSelectAlumno(alumno: Alumno) {
        _state.update {
            it.copy(
                alumnoSeleccionado = alumno,
                error = null,
                mensaje = null
            )
        }
    }

    fun onDeleteAlumno() {
        val seleccionado = _state.value.alumnoSeleccionado
        if (seleccionado == null) {
            _state.update {
                it.copy(
                    error = "Selecciona un alumno primero",
                    mensaje = null
                )
            }
            return
        }

        viewModelScope.launch {
            deleteAlumnoUseCase.deleteAlumno(seleccionado)
            val listaActualizada = cargarAlumnosUseCase.cargarAlumnos()
            _state.update {
                it.copy(
                    alumnos = listaActualizada,
                    alumnoSeleccionado = null,
                    error = null,
                    mensaje = "Alumno eliminado"
                )
            }
        }
    }

    fun clearMessages() {
        _state.update { it.copy(error = null, mensaje = null) }
    }
}

class MainViewModelFactory(
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private val deleteAlumnoUseCase: DeleteAlumnoUseCase,
    private val cargarAlumnosUseCase: CargarAlumnosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addAlumnoUseCase, deleteAlumnoUseCase, cargarAlumnosUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}