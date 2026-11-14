package com.example.controlinventariocompose.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.controlinventariocompose.domain.model.Producto
import com.example.controlinventariocompose.domain.useCases.AddProductoUseCase
import com.example.controlinventariocompose.domain.useCases.CargarProductosUseCase
import com.example.controlinventariocompose.domain.useCases.DeleteProductoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addProductoUseCase: AddProductoUseCase ,
    private val deleteProductoUseCase: DeleteProductoUseCase ,
    private val cargarProductosUseCase: CargarProductosUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    // 2. El estado público de sólo lectura que observará la pantalla
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarProductosUseCase.cargarProductos()
            _state.update { it.copy(productos = listaInicial) }
        }
    }

    fun onChangeProducto(productoForm: Producto){
        _state.update {
            it.copy(producto = productoForm,
                error = null)

        }
    }

    fun onAddProducto(){
        viewModelScope.launch {
            _state.update { it.copy(error = null) }
            val nuevoProducto = _state.value.producto

            addProductoUseCase.addProducto(nuevoProducto)

            val listaActualizada = cargarProductosUseCase.cargarProductos()
            _state.update {
                it.copy(
                    productos =  listaActualizada,
                    producto = Producto(
                        nombre = "",
                        categoria = "",
                        precio = 0.00,
                        stock = 0,
                        disponible = false
                    )
                )
            }
        }
    }

    fun onDeleteProducto(producto: Producto){
        viewModelScope.launch {
            deleteProductoUseCase.deleteProducto(producto)

            val listaActualizada = cargarProductosUseCase.cargarProductos()

            _state.update { it.copy(productos = listaActualizada) }
        }
    }



}


class MainViewModelFactory(
    private val addProductoUseCase: AddProductoUseCase ,
    private val deleteProductoUseCase: DeleteProductoUseCase ,
    private val cargarProductosUseCase: CargarProductosUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.controlinventariocompose.ui.main.MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainViewModel(
               addProductoUseCase,
                deleteProductoUseCase,
                cargarProductosUseCase

            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}