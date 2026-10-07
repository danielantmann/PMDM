package com.example.mvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm.domain.useCases.DamePresidenteUseCase

class MainViewModel (
    private val damePresidenteUseCase: DamePresidenteUseCase
    ): ViewModel(){

    private val _state = MutableLiveData(MainState(presidente = "inicio"))
    val state: LiveData<MainState> = _state

    fun handleDamePresidente(){
        val presidente = damePresidenteUseCase.damePresidente()
        val texto = "${presidente.nombre} (${presidente.partido})"

        _state.value = _state.value?.copy(presidente = texto)
            ?: MainState(presidente = texto)
    }
}

class MainViewModelFactory(
    private val damePresidenteUseCase: DamePresidenteUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(damePresidenteUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}