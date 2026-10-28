package com.example.remakeexam.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _state = MutableLiveData(MainState())
    val state: LiveData<MainState> = _state

    fun toggleAllTasks() {
        val currentState = _state.value ?: MainState()
        val newState = !currentState.areAllChecked
        _state.value = currentState.copy(areAllChecked = newState)
    }

    fun onBottomNavClicked(buttonName: String) {
        val currentState = _state.value ?: MainState()

        // Si pinchan el mismo botón, sumamos 1 al contador.
        // (O si quieres un contador por cada botón, guardarías un mapa, pero con un contador general suele valer para estos ejercicios).
        val newCount = if (currentState.selectedNavButon == buttonName) {
            currentState.clickCount + 1
        } else {
            1 // Reinicia a 1 si cambia de pestaña, o ponle +1 si prefieres que sea acumulativo global
        }

        _state.value = currentState.copy(
            selectedNavButon = buttonName,
            clickCount = newCount
        )
    }
}