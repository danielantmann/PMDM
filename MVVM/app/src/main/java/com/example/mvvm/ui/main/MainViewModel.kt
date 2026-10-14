package com.example.mvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.di.AppModule
import com.example.mvvm.di.AppModule.delPoliticoUseCase
import com.example.mvvm.domain.model.Politico
import com.example.mvvm.domain.useCases.AddPoliticoUseCase
import com.example.mvvm.domain.useCases.CargarPoliticiosUseCase
import com.example.mvvm.domain.useCases.DamePresidenteUseCase
import com.example.mvvm.domain.useCases.DelPoliticoUseCase

class MainViewModel (
    private val damePresidenteUseCase: DamePresidenteUseCase,
    private val addPoliticoUseCase: AddPoliticoUseCase,
    private val cargarPoliticiosUseCase: CargarPoliticiosUseCase,
    private val delPoliticoUseCase: DelPoliticoUseCase,
    ): ViewModel(){

    private val _state = MutableLiveData(MainState(presidente = "inicio",listOf()))
    val state: LiveData<MainState> = _state

    init {
        handleCargaLista()
    }

    fun handleDamePresidente(){
        val presidente = damePresidenteUseCase.damePresidente()
        _state.value = _state.value?.copy(presidente = presidente.toString())
    }

    fun handleCargaLista() {
        _state.value = _state.value?.copy(politicos = cargarPoliticiosUseCase.cargarLista())
    }

    fun handleAdd(p: Politico) {

        try {
            addPoliticoUseCase.addPolitico(p)
            _state.value = _state.value?.copy(politicos = cargarPoliticiosUseCase.cargarLista())
        }
        catch (e: Exception){
            _state.value = _state.value?.copy(error= e.message)
        }

    }

    fun handleDelete(p: Politico) {

        try {
            AppModule.delPoliticoUseCase.delPolitico(p)
            _state.value = _state.value?.copy(politicos = cargarPoliticiosUseCase.cargarLista())
        }
        catch (e: Exception){
            _state.value = _state.value?.copy(error= e.message)
        }
    }

    fun handleLimpiarError(){
        _state.value = _state.value?.copy(error = null)
    }



}


class MainViewModelFactory(
    private val damePresidenteUseCase: DamePresidenteUseCase,
    private val addPoliticoUseCase: AddPoliticoUseCase,
    private val cargarPoliticiosUseCase: CargarPoliticiosUseCase,
    private val delPoliticoUseCase: DelPoliticoUseCase,
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                damePresidenteUseCase,
                addPoliticoUseCase,
                cargarPoliticiosUseCase,
                delPoliticoUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}