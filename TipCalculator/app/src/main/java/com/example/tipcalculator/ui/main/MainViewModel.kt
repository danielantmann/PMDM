package com.example.tipcalculator.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tipcalculator.domain.model.Propina
import com.example.tipcalculator.domain.useCases.CalcularPropinaUseCase
import com.example.tipcalculator.domain.useCases.GuardarPropinasUseCase
import com.example.tipcalculator.domain.useCases.ObtenerListaPropinasUseCase

class MainViewModel(
    private val calcularPropinaUseCase: CalcularPropinaUseCase,
    private val guardarPropinasUseCase: GuardarPropinasUseCase,
    private val obtenerListaPropinasUseCase: ObtenerListaPropinasUseCase
) : ViewModel(){

    private val _state = MutableLiveData(MainState(resultado = 0.00, totalGuardados = 0))
    val state: MutableLiveData<MainState> = _state

    fun handleCalcularPropina(totalStr: String, porcentajeStr: String ){
        if (totalStr.isNullOrBlank() || porcentajeStr.isNullOrBlank()){
            _state.value = _state.value?.copy(
                error = "Por favor introduzca un numero"
            )
            return
        }
        val total = totalStr.toDoubleOrNull()
        val porcentaje = porcentajeStr.toDoubleOrNull()

        if (total == null || porcentaje == null) {
            _state.value = _state.value?.copy(
                error = "Por favor introduzca un número válido"
            )
            return
        }

        val resultadoPropina = calcularPropinaUseCase.calcularPropinas(total,porcentaje)
        guardarPropinasUseCase.guardarPropinas(Propina(
                precio = total,
                porcentaje = porcentaje,
                resultado = resultadoPropina
        ))

        val totalGuardados = obtenerListaPropinasUseCase.obtenerListaPropinas()
        _state.value = _state.value?.copy(
            resultado = resultadoPropina,
            totalGuardados =totalGuardados.size,
            error = null
        )

    }

    class MainViewModelFactory(
        private val calcularPropinaUseCase: CalcularPropinaUseCase,
        private val guardarPropinasUseCase: GuardarPropinasUseCase,
        private val obtenerListaPropinasUseCase: ObtenerListaPropinasUseCase,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.tipcalculator.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    calcularPropinaUseCase,
                    guardarPropinasUseCase,
                    obtenerListaPropinasUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}