package com.example.bibliotecacompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bibliotecacompose.domain.model.Libro
import com.example.bibliotecacompose.domain.useCases.AddLibroUseCase
import com.example.bibliotecacompose.domain.useCases.CargarLibrosUseCase
import com.example.bibliotecacompose.domain.useCases.DeleteLibroUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addLibroUseCase: AddLibroUseCase,
    private val deleteLibroUseCase: DeleteLibroUseCase,
    private val cargarLibrosUseCase: CargarLibrosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarLibrosUseCase.cargarLibros()
            _state.update { it.copy(libros = listaInicial) }
        }
    }

    fun onChangeLibro(libroForm: Libro) {
        _state.update {
            it.copy(
                libro = libroForm,
                error = null,
                mensaje = null
            )
        }
    }

    fun onAddLibro() {
        viewModelScope.launch {
            val form = _state.value.libro

            if (form.nombre.isBlank()) {
                _state.update { it.copy(error = "El nombre es obligatorio", mensaje = null) }
                return@launch
            }

            addLibroUseCase.addLibro(form)

            val listaActualizada = cargarLibrosUseCase.cargarLibros()
            _state.update {
                it.copy(
                    libros = listaActualizada,
                    libro = Libro(),
                    error = null,
                    mensaje = "Libro añadido"
                )
            }
        }
    }

    fun onSelectLibro(libro: Libro) {
        _state.update {
            it.copy(
                libroSeleccionado = libro,
                error = null,
                mensaje = null
            )
        }
    }

    fun onDeleteLibro() {
        val seleccionado = _state.value.libroSeleccionado
        if (seleccionado == null) {
            _state.update {
                it.copy(error = "Selecciona un libro primero", mensaje = null)
            }
            return
        }

        viewModelScope.launch {
            deleteLibroUseCase.deleteLibro(seleccionado)
            val listaActualizada = cargarLibrosUseCase.cargarLibros()
            _state.update {
                it.copy(
                    libros = listaActualizada,
                    libroSeleccionado = null,
                    error = null,
                    mensaje = "Libro eliminado"
                )
            }
        }
    }

    fun clearMessages() {
        _state.update { it.copy(error = null, mensaje = null) }
    }
}

class MainViewModelFactory(
    private val addLibroUseCase: AddLibroUseCase,
    private val deleteLibroUseCase: DeleteLibroUseCase,
    private val cargarLibrosUseCase: CargarLibrosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addLibroUseCase,
                deleteLibroUseCase,
                cargarLibrosUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}