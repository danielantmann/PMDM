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
): ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarLibrosUseCase.cargarLibros()
            _state.update { it.copy(libros = listaInicial) }
        }
    }

    fun onChangeLibro(libroForm: Libro){
        _state.update {
            it.copy(
                libro = libroForm,
                error = null
            )
        }
    }

    fun onAddLibro(){
        viewModelScope.launch {
            _state.update { it.copy(error = null) }

            val nuevoLibro = _state.value.libro
            addLibroUseCase.addLibro(nuevoLibro)

            val listaActualizada = cargarLibrosUseCase.cargarLibros()

            _state.update {
                it.copy(
                    libros = listaActualizada,
                    libro= Libro(
                        nombre = "",
                        autor = "",
                        anio = 0,
                        categoria = "",
                        disponible = false
                    )
                )
            }
        }
    }

    fun onDeleteLibro(libro: Libro){
        viewModelScope.launch {
            deleteLibroUseCase.deleteLibro(libro)

            val listaActualizada = cargarLibrosUseCase.cargarLibros()

            _state.update {
                it.copy(
                    libros = listaActualizada,
                )
            }
        }

    }
}




class MainViewModelFactory(
    private val addLibroUseCase: AddLibroUseCase,
    private val deleteLibroUseCase: DeleteLibroUseCase,
    private val cargarLibrosUseCase: CargarLibrosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.bibliotecacompose.ui.main.MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainViewModel(
                addLibroUseCase, deleteLibroUseCase, cargarLibrosUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
