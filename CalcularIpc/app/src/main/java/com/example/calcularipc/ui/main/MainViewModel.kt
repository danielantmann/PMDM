package com.example.calcularipc.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calcularipc.domain.useCases.CalcularImcUseCase

class MainViewModel (
    private val calcularImcUseCase: CalcularImcUseCase,
): ViewModel() {

    private val _state = MutableLiveData(MainState(resultadoImc = ""))
    val state: MutableLiveData<MainState> = _state

    fun handleCalcularIpc(txtNombre: String ,txtPeso: String, txtAltura: String ){
        if (txtNombre.isNullOrBlank() || txtPeso.isNullOrBlank() || txtAltura.isNullOrBlank()){
            _state.value = _state.value?.copy(error = "Rellena todos los campos")
            return
        }
        val pesoDoubble = txtPeso.toDoubleOrNull()
        val alturaDoubble = txtAltura.toDoubleOrNull()
        if (pesoDoubble == null){
            _state.value = _state.value?.copy(error = "El peso debe ser un numero")
            return
        }
        if (alturaDoubble == null){
            _state.value = _state.value?.copy(error = "La altura debe ser un numero")
            return
        }

        val resultado = calcularImcUseCase.calcularImc(pesoDoubble,alturaDoubble)
        val mensaje = "${txtNombre} : ${resultado}"
        _state.value = _state.value?.copy(
            resultadoImc= mensaje,
            error = null,
        )
    }


    class MainViewModelFactory(
        private val calcularImcUseCase: CalcularImcUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.calcularipc.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(calcularImcUseCase) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}