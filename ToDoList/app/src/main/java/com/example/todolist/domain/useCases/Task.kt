package com.example.taskmate

enum class Priority { ALTA, MEDIA, BAJA }

// MODEL
data class Task(
    val id: Int,
    val title: String,
    val subtitle: String,
    val priority: Priority,
    val checked: Boolean = false
)
