package com.example.conversorfarenheit.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.conversorfarenheit.domain.model.Conversion
import com.example.conversorfarenheit.domain.useCases.ConvertirGradosUseCase
import com.example.conversorfarenheit.domain.useCases.GurdarConversionesUseCase
import com.example.conversorfarenheit.domain.useCases.ObtenerConversionesUseCase

class MainViewModel(
        private val convertirGradosUseCase: ConvertirGradosUseCase,
        private val gurdarConversionesUseCase: GurdarConversionesUseCase,
        private val obtenerConversionesUseCase: ObtenerConversionesUseCase,
): ViewModel() {
    private val _state = MutableLiveData(MainState(resultado = 0.00))
    val state: MutableLiveData<MainState> = _state

    fun handleConverirGrados(grados: String){
        if (grados.isNullOrBlank()){
            _state.value = _state.value?.copy(error = "Por favor introduzca un numero")
            return
        }

        val gradosConvertios = convertirGradosUseCase.convertirGrados(grados)
        val gradosGuardar = Conversion(
            grados = gradosConvertios
        )
        gurdarConversionesUseCase.guardarConversiones(gradosGuardar)

        _state.value = _state.value?.copy(
            resultado = gradosConvertios,
            error = null
        )

    }

    class MainViewModelFactory(
        private val convertirGradosUseCase: ConvertirGradosUseCase,
        private val gurdarConversionesUseCase: GurdarConversionesUseCase,
        private val obtenerConversionesUseCase: ObtenerConversionesUseCase,

        ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.example.conversorfarenheit.ui.main.MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(
                    convertirGradosUseCase,
                    gurdarConversionesUseCase,
                    obtenerConversionesUseCase,
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}