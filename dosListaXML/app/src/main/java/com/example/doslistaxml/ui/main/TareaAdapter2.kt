package com.example.doslistaxml.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doslistaxml.R
import com.example.doslistaxml.domain.model.Tarea

class TareaAdapter2(
    private val onSelectClick: (Tarea) -> Unit,
    private val onMoverClick: (Tarea) -> Unit
) : ListAdapter<Tarea, TareaAdapter2.TareaViewHolder>(TareaDiffCallback2()) {

    var idSeleccionada: Long? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(vista, onSelectClick, onMoverClick)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = getItem(position)
        holder.bind(tarea, tarea.id == idSeleccionada)
    }

    class TareaViewHolder(
        itemView: View,
        private val onSelectClick: (Tarea) -> Unit,
        private val onMoverClick: (Tarea) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        // Aquí usamos findViewById de forma nativa, sin dependencias de ViewBinding
        private val cardRoot: CardView = itemView.findViewById(R.id.cardItemRoot)
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        private val btnMover: Button = itemView.findViewById(R.id.btnMover)

        fun bind(tarea: Tarea, isSelected: Boolean) {
            tvNombre.text = tarea.nombre
            tvDescripcion.text = tarea.descripcion

            // Color de selección
            cardRoot.setCardBackgroundColor(
                if (isSelected) Color.parseColor("#E0F7FA")
                else Color.WHITE
            )

            // Clic en la tarjeta
            cardRoot.setOnClickListener {
                onSelectClick(tarea)
            }

            // Clic en el botón lateral
            btnMover.setOnClickListener {
                onMoverClick(tarea)
            }
        }
    }
}

class TareaDiffCallback2 : DiffUtil.ItemCallback<Tarea>() {
    override fun areItemsTheSame(oldItem: Tarea, newItem: Tarea): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Tarea, newItem: Tarea): Boolean = oldItem == newItem
}