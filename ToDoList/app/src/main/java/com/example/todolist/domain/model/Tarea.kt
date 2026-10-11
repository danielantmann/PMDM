package com.example.todolist.domain.model

import java.util.UUID

data class Tarea(
    val id:String = UUID.randomUUID().toString(),
    val tarea: String
)
