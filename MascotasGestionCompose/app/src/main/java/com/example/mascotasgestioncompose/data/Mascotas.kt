package com.example.mascotasgestioncompose.data

import com.example.mascotasgestioncompose.domain.model.Mascota

object Mascotas {
    private var nextId =  1L

    private val mascotas = mutableListOf(
        Mascota(
            id = nextId ++,
            nombre = "Thor",
            especie = "Perro",
            edad  = 11,
            dueño = "Daniel"
        ),
        Mascota(
            id = nextId ++,
            nombre = "Cindy",
            especie = "Perro",
            edad  = 11,
            dueño = "Daniel"
        )
    )

    fun getListaMascotas() = mascotas.toList()

    fun addMascota(m : Mascota){
        mascotas.add(m.copy(id = nextId++))
    }

    fun deleteMascota(m: Mascota){
        mascotas.removeAll { it.id == m.id }
    }
}