package com.example.calcularipc.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.calcularipc.R
import com.example.calcularipc.domain.model.ImcRegistro

class ImcAdapter(private val listaRegistros: List<ImcRegistro>) : RecyclerView.Adapter<ImcAdapter.ViewHolder>() {

    // 1. ViewHolder: Vincula los componentes visuales de tu plantilla (item_imc.xml)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById(R.id.tvNombreItem)
        val tvImc: TextView = view.findViewById(R.id.tvImcItem)
    }

    // 2. Crea/Infla visualmente la fila a partir de tu xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_imc, parent, false)
        return ViewHolder(view)
    }

    // 3. Rellena los datos de cada elemento según su posición en la lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = listaRegistros[position]
        holder.tvNombre.text = registro.nombre
        holder.tvImc.text = registro.imc.toString()
    }

    // 4. Devuelve el número total de elementos que tiene la lista
    override fun getItemCount(): Int = listaRegistros.size
}