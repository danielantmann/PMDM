package com.example.todolist.data

import com.example.todolist.domain.model.Tarea

object Tareas {
    private val listaTareas = mutableListOf<Tarea>(
        Tarea(tarea = "Estudiar Clean Architecture"),
        Tarea(tarea = "Hacer la compra"),
        Tarea(tarea = "Sacar al perro")
    )

    fun guardarTarea(tarea: Tarea){
        listaTareas.add(tarea)
    }

    fun eliminarTarea(id: String){
        listaTareas.removeAll { it.id == id }
    }

    fun obtenerTarea(): List<Tarea>{
        return listaTareas
    }
}