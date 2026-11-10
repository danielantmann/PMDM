package com.example.examen.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.examen.domain.model.Alumno
import com.example.examen.domain.useCases.AddAlumnoUseCase
import com.example.examen.domain.useCases.CargarListaAlumnosUseCase
import com.example.examen.domain.useCases.EliminarAlumnoUseCase

class MainViewModel(
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private  val eliminarAlumnoUseCase: EliminarAlumnoUseCase,
    private val cargarListaAlumnosUseCase: CargarListaAlumnosUseCase
): ViewModel (){
    private val _state = MutableLiveData(MainState(listOf()))

    val state: LiveData<MainState> = _state

    init {
        handleCargarLista()
    }


    fun handleCargarLista(){
      _state.value = _state.value?.copy(cargarListaAlumnosUseCase.cargarLista())
    }

    fun handleAdd(a: Alumno){
        addAlumnoUseCase.addAlumno(a)
        _state.value = _state.value?.copy(cargarListaAlumnosUseCase.cargarLista())

    }

    fun handleDelete(){
        eliminarAlumnoUseCase.deleteAlumno()
        _state.value = _state.value?.copy(cargarListaAlumnosUseCase.cargarLista())
    }

}

class MainViewModelFactory(
    private val addAlumnoUseCase: AddAlumnoUseCase,
    private  val eliminarAlumnoUseCase: EliminarAlumnoUseCase,
    private val cargarListaAlumnosUseCase: CargarListaAlumnosUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.examen.ui.main.MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addAlumnoUseCase, eliminarAlumnoUseCase, cargarListaAlumnosUseCase

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}