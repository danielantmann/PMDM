package com.example.mascotasgestioncompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mascotasgestioncompose.domain.model.Mascota
import com.example.mascotasgestioncompose.domain.useCases.AddMascotaUseCase
import com.example.mascotasgestioncompose.domain.useCases.CargarListaMascotasUseCase
import com.example.mascotasgestioncompose.domain.useCases.DeleteMascotaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addMascotaUseCase: AddMascotaUseCase,
    private val deleteMascotaUseCase: DeleteMascotaUseCase,
    private val cargarListaMascotasUseCase: CargarListaMascotasUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarListaMascotasUseCase.cargarListaMascotas()
            _state.update { it.copy(mascotas = listaInicial) }
        }
    }

    fun onChangeMascota(pedidoForm: Mascota) {
        _state.update {
            it.copy(
                mascota = pedidoForm,
                error = null,
                mensaje = null
            )
        }
    }

    fun onAddMascota() {
        viewModelScope.launch {
            val form = _state.value.mascota

            if (form.nombre.isBlank()) {
                _state.update { it.copy(error = "El nombre es obligatorio", mensaje = null) }
                return@launch
            }

            addMascotaUseCase.addMascota(form)

            val listaActualizada = cargarListaMascotasUseCase.cargarListaMascotas()
            _state.update {
                it.copy(
                    mascotas = listaActualizada,
                    mascota = Mascota(),
                    error = null,
                    mensaje = "Mascota añadido"
                )
            }
        }
    }

    fun onSelectMascota(mascota: Mascota) {
        _state.update {
            it.copy(
                mascotaSelecionada = mascota,
                error = null,
                mensaje = null
            )
        }
    }

    fun onDeleteMascota() {
        val seleccionado = _state.value.mascotaSelecionada
        if (seleccionado == null) {
            _state.update {
                it.copy(error = "Selecciona un pedido primero", mensaje = null)
            }
            return
        }

        viewModelScope.launch {
            deleteMascotaUseCase.deleteMascota(seleccionado)
            val listaActualizada = cargarListaMascotasUseCase.cargarListaMascotas()
            _state.update {
                it.copy(
                    mascotas = listaActualizada,
                    mascotaSelecionada = null,
                    error = null,
                    mensaje = "Pedido eliminado"
                )
            }
        }
    }

    fun clearMessages() {
        _state.update { it.copy(error = null, mensaje = null) }
    }

}

class MainViewModelFactory(
    private val addMascotaUseCase: AddMascotaUseCase,
    private val deleteMascotaUseCase: DeleteMascotaUseCase,
    private val cargarListaMascotasUseCase: CargarListaMascotasUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.mascotasgestioncompose.ui.main.MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                    addMascotaUseCase, deleteMascotaUseCase, cargarListaMascotasUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}