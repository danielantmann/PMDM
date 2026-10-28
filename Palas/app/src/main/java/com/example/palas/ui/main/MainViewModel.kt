package com.example.palas.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.palas.domain.model.Pala
import com.example.palas.domain.useCases.AddPalaUseCase
import com.example.palas.domain.useCases.CargarPalasUseCase
import com.example.palas.domain.useCases.DamePalaUseCase
import com.example.palas.domain.useCases.DeletePalaUseCase

class MainViewModel(
    private val damePalaUseCase: DamePalaUseCase,
    private val cargarPalasUseCase: CargarPalasUseCase,
    private val addPalaUseCase: AddPalaUseCase,
    private val deletePalaUseCase: DeletePalaUseCase,
): ViewModel(){
    private val _state = MutableLiveData<MainState>(MainState("Inicio", listOf()))
    val state: LiveData<MainState> = _state

    init {
        handleCargarLista()
    }


    fun handleDameJugador() {
        var pala = damePalaUseCase.damePala()

        _state.value = _state.value?.copy(pala = pala.toString())
    }

    fun handleCargarLista() {
        _state.value = _state.value?.copy(palas = cargarPalasUseCase.cargarPalas())
    }

    fun handleAdd(p: Pala) {
       addPalaUseCase.addPala(p)
        _state.value = _state.value?.copy(palas = cargarPalasUseCase.cargarPalas())
    }

    fun handleDelete(p : Pala){
        deletePalaUseCase.deletePala(p)
        _state.value = _state.value?.copy(palas = cargarPalasUseCase.cargarPalas())
    }


    class MainViewModelFactory(
        private val damePalaUseCase: DamePalaUseCase,
        private val cargarPalasUseCase: CargarPalasUseCase,
        private val addPalaUseCase: AddPalaUseCase,
        private val deletePalaUseCase: DeletePalaUseCase,
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.palas.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return MainViewModel(
                    damePalaUseCase,
                    cargarPalasUseCase,
                    addPalaUseCase,
                    deletePalaUseCase

                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}