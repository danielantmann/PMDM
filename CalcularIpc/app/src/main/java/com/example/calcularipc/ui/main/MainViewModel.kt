package com.example.calcularipc.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calcularipc.data.ImcRegitros
import com.example.calcularipc.domain.model.ImcRegistro
import com.example.calcularipc.domain.useCases.CalcularImcUseCase
import com.example.calcularipc.domain.useCases.EliminarRegistroUseCase
import com.example.calcularipc.domain.useCases.GuardarRegistroUseCase
import com.example.calcularipc.domain.useCases.ObtenerClasificacionImcUseCase
import com.example.calcularipc.domain.useCases.ObtenerRegistrosUseCase

class MainViewModel (
    private val calcularImcUseCase: CalcularImcUseCase,
    private val clasificacionImcUseCase: ObtenerClasificacionImcUseCase,
    private val obtenerRegistrosUseCase: ObtenerRegistrosUseCase,
    private val guardarRegistroUseCase: GuardarRegistroUseCase,
    private val eliminarRegistroUseCase: EliminarRegistroUseCase
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

        val resultadoImc = calcularImcUseCase.calcularImc(pesoDoubble,alturaDoubble)
        val resultadoTexto = clasificacionImcUseCase.clasificarImc(resultadoImc)
        val mensaje = "${txtNombre} : ${resultadoTexto}"
        val registro = ImcRegistro(
            nombre = txtNombre,
            imc = resultadoImc,
            resultado = resultadoTexto
        )

        guardarRegistroUseCase.guardarRegistro(registro)

        _state.value = _state.value?.copy(
            resultadoImc= mensaje,
            error = null,
            listaImc = obtenerRegistrosUseCase.obtenerRegistros()
        )

    }

    fun handleEliminarRegistro(id: String){
        eliminarRegistroUseCase.eliminarRegistro(id)

        _state.value = _state.value?.copy(
            listaImc = obtenerRegistrosUseCase.obtenerRegistros()
        )
    }


    class MainViewModelFactory(
        private val calcularImcUseCase: CalcularImcUseCase,
        private val clasificacionImcUseCase: ObtenerClasificacionImcUseCase,
        private val obtenerRegistrosUseCase: ObtenerRegistrosUseCase,
        private val guardarRegistroUseCase: GuardarRegistroUseCase,
        private val eliminarRegistroUseCase: EliminarRegistroUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.calcularipc.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    calcularImcUseCase,
                    clasificacionImcUseCase,
                    obtenerRegistrosUseCase,
                    guardarRegistroUseCase,
                    eliminarRegistroUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}