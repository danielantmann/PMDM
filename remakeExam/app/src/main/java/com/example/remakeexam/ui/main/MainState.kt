package com.example.remakeexam.ui.main

data class MainState(
    val areAllChecked: Boolean = false,
    val selectedNavButon: String = "inicio",
    val clickCount: Int = 0
)
