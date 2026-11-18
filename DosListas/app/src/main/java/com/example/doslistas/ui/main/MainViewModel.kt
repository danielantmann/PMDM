package com.example.doslistas.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.doslistas.domain.model.Tarea
import com.example.doslistas.domain.useCases.AddTareaUseCase
import com.example.doslistas.domain.useCases.CargarTareasUseCase
import com.example.doslistas.domain.useCases.DeleteTareaUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val addTareaUseCase: AddTareaUseCase,
    private val deleteTareaUseCase: DeleteTareaUseCase,
    private val cargarTareasUseCase: CargarTareasUseCase
): ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val listaInicial = cargarTareasUseCase.cargarTareas()
            _state.update { it.copy(tareasPendientes = listaInicial) }
        }
    }

    fun onChangeTarea(tareaForm: Tarea){
        _state.update {
            it.copy(
                tarea = tareaForm,
                error = null,
                mensaje = null
            )
        }
    }

    fun onAddTarea(){
        viewModelScope.launch {
            _state.update { it.copy(error = null) }
            val nuevaTarea = _state.value.tarea

            if (nuevaTarea.nombre.isBlank()) {
                _state.update { it.copy(error = "El nombre es obligatorio", mensaje = null) }
                return@launch
            }
            if (nuevaTarea.descripcion.isBlank()) {
                _state.update { it.copy(error = "la descripcion es obligatoria", mensaje = null) }
                return@launch
            }

            addTareaUseCase.addTarea(nuevaTarea)

            val listaActualizada = cargarTareasUseCase.cargarTareas()

            _state.update {
                it.copy(
                    tareasPendientes = listaActualizada,
                    tarea = Tarea(),
                    error = null,
                    mensaje = "tarea añadido"
                )
            }
        }
    }

    fun onSelectTarea(tarea: Tarea) {
        _state.update {
            it.copy(
                tareaSeleccionado = tarea,
                error = null,
                mensaje = null
            )
        }
    }

    fun onDeleteTarea() {
        val seleccionado = _state.value.tareaSeleccionado
        if (seleccionado == null) {
            _state.update {
                it.copy(
                    error = "Selecciona un alumno primero",
                    mensaje = null
                )
            }
            return
        }

        viewModelScope.launch {
            deleteTareaUseCase.deleteTarea(seleccionado)
            val listaActualizada = cargarTareasUseCase.cargarTareas()
            _state.update {
                it.copy(
                    tareasPendientes = listaActualizada,
                    tareaSeleccionado = null,
                    error = null,
                    mensaje = "Alumno eliminado"
                )
            }
        }
    }

    fun moverTareaEntreListas2(tarea: Tarea) {
        _state.update { current ->
            // 1. Quitamos la tarea de la lista 1 (pendientes)
            val nuevasPendientes = current.tareasPendientes.filter { it.id != tarea.id }

            // 2. Añadimos la misma tarea a la lista 2 (completadas)
            val nuevasCompletadas = current.tareasCompletadas + tarea

            // 3. Devolvemos el nuevo estado con las dos listas actualizadas
            current.copy(
                tareasPendientes = nuevasPendientes,
                tareasCompletadas = nuevasCompletadas,
                mensaje = "Tarea movida a la otra lista"
            )
        }
    }

    fun moverTareaEntreListas() {
        val seleccionado = _state.value.tareaSeleccionado
        if (seleccionado == null) {
            _state.update {
                it.copy(error = "Selecciona una tarea primero", mensaje = null)
            }
            return
        }

        _state.update { current ->
            // Comprobamos si está en pendientes para pasarla a completadas
            if (current.tareasPendientes.any { it.id == seleccionado.id }) {
                current.copy(
                    tareasPendientes = current.tareasPendientes.filter { it.id != seleccionado.id },
                    tareasCompletadas = current.tareasCompletadas + seleccionado.copy(isTerminada = true),
                    tareaSeleccionado = null, // Limpiamos la selección
                    mensaje = "Tarea '${seleccionado.nombre}' movida a completadas"
                )
            } else {
                // Si ya estaba en completadas, la devolvemos a pendientes
                current.copy(
                    tareasCompletadas = current.tareasCompletadas.filter { it.id != seleccionado.id },
                    tareasPendientes = current.tareasPendientes + seleccionado.copy(isTerminada = false),
                    tareaSeleccionado = null, // Limpiamos la selección
                    mensaje = "Tarea '${seleccionado.nombre}' devuelta a pendientes"
                )
            }
        }
    }

    // Versión 2: Mueve la tarea directamente al recibirla por parámetro (clic directo o botón en card)
    fun moverTareaDirecta(tarea: Tarea) {
        viewModelScope.launch {
            val tareaModificada = tarea.copy(isTerminada = !tarea.isTerminada)
            addTareaUseCase.addTarea(tareaModificada)

            // Usamos tu método real de carga
            val todasLasTareas = cargarTareasUseCase.cargarTareas()
            _state.update {
                it.copy(
                    tareasPendientes = todasLasTareas.filter { t -> !t.isTerminada },
                    tareasCompletadas = todasLasTareas.filter { t -> t.isTerminada },
                    tareaSeleccionado = null,
                    mensaje = if (tareaModificada.isTerminada) "Tarea movida a completadas" else "Tarea devuelta a pendientes"
                )
            }
        }
    }

    fun clearMessages() {
        _state.update { it.copy(error = null, mensaje = null) }
    }

}

class MainViewModelFactory(
    private val addTareaUseCase: AddTareaUseCase,
    private val deleteTareaUseCase: DeleteTareaUseCase,
    private val cargarTareasUseCase: CargarTareasUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.doslistas.ui.main.MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
              addTareaUseCase, deleteTareaUseCase, cargarTareasUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}