package com.example.cafeteriacompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.cafeteriacompose.domain.model.Pedido
import com.example.cafeteriacompose.domain.useCases.AddPedidoUseCase
import com.example.cafeteriacompose.domain.useCases.CargarPedidosUseCase
import com.example.cafeteriacompose.domain.useCases.DeletePedidoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addPedidoUseCase: AddPedidoUseCase,
    private val deletePedidoUseCase: DeletePedidoUseCase,
    private val cargarPedidosUseCase: CargarPedidosUseCase
): ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarPedidosUseCase.cargarPedidos()
            _state.update { it.copy(pedidos = listaInicial) }
        }
    }

    fun onChangePedido(pedidoForm: Pedido) {
        _state.update {
            it.copy(
                pedido = pedidoForm,
                error = null,
                mensaje = null
            )
        }
    }

    fun onAddPedido() {
        viewModelScope.launch {
            val form = _state.value.pedido

            if (form.nombre.isBlank()) {
                _state.update { it.copy(error = "El nombre es obligatorio", mensaje = null) }
                return@launch
            }

            addPedidoUseCase.addPedido(form)

            val listaActualizada = cargarPedidosUseCase.cargarPedidos()
            _state.update {
                it.copy(
                    pedidos = listaActualizada,
                    pedido = Pedido(),
                    error = null,
                    mensaje = "Pedido añadido"
                )
            }
        }
    }

    fun onSelectPedido(pedido: Pedido) {
        _state.update {
            it.copy(
                pedidoSeleccionado = pedido,
                error = null,
                mensaje = null
            )
        }
    }

    fun onDeletePedido() {
        val seleccionado = _state.value.pedidoSeleccionado
        if (seleccionado == null) {
            _state.update {
                it.copy(error = "Selecciona un pedido primero", mensaje = null)
            }
            return
        }

        viewModelScope.launch {
            deletePedidoUseCase.deletePedido(seleccionado)
            val listaActualizada = cargarPedidosUseCase.cargarPedidos()
            _state.update {
                it.copy(
                    pedidos = listaActualizada,
                    pedidoSeleccionado = null,
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
    private val addPedidoUseCase: AddPedidoUseCase,
    private val deletePedidoUseCase: DeletePedidoUseCase,
    private val cargarPedidosUseCase: CargarPedidosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.cafeteriacompose.ui.main.MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addPedidoUseCase,
                deletePedidoUseCase,
                cargarPedidosUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}