package com.example.myapplication.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.useCases.DamePresidenteUseCase

class MainViewModel (val damePresidenteUseCase: DamePresidenteUseCase) : ViewModel(){

    private val  _state = MutableLiveData<MainState>(MainState("inicio"))
}