package com.example.todolist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.domain.model.Tarea
import com.example.todolist.R

class TareaListAdapter(private val listaTareas: List<Tarea>,
                       private val onItemClick: (String) -> Unit // Lambda que recibe el ID al hacer clic
) : RecyclerView.Adapter<TareaListAdapter.ViewHolder>() {

    // 1. ViewHolder: Vincula los componentes visuales de tu plantilla (item_imc.xml)
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTarea: TextView = view.findViewById(R.id.tvTareaItem)


    }

    // 2. Crea/Infla visualmente la fila a partir de tu xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarea, parent, false)
        return ViewHolder(view)
    }

    // 3. Rellena los datos de cada elemento según su posición en la lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val registro = listaTareas[position]
        holder.tvTarea.text = registro.tarea


        // ¡Aquí está la clave! Al pulsar en CUALQUIER parte de la celda:
        holder.itemView.setOnClickListener {
            onItemClick(registro.id) // Enviamos el ID al MainActivity
        }
    }

    // 4. Devuelve el número total de elementos que tiene la lista
    override fun getItemCount(): Int = listaTareas.size
}