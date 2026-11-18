package com.example.doslistaxml.ui.main

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.doslistaxml.databinding.ItemTareaBinding
import com.example.doslistaxml.domain.model.Tarea

class TareaAdapter(
    private val onSelectClick: (Tarea) -> Unit,
    private val onMoverClick: (Tarea) -> Unit
) : ListAdapter<Tarea, TareaAdapter.TareaViewHolder>(TareaDiffCallback()) {

    var idSeleccionada: Long? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ItemTareaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TareaViewHolder(binding, onSelectClick, onMoverClick)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = getItem(position)
        holder.bind(tarea, tarea.id == idSeleccionada)
    }

    class TareaViewHolder(
        private val binding: ItemTareaBinding,
        private val onSelectClick: (Tarea) -> Unit,
        private val onMoverClick: (Tarea) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tarea: Tarea, isSelected: Boolean) {
            with(binding) {
                tvNombre.text = tarea.nombre
                tvDescripcion.text = tarea.descripcion

                // Cambia el color de fondo si la tarjeta está seleccionada
                cardItemRoot.setCardBackgroundColor(
                    if (isSelected) Color.parseColor("#E0F7FA")
                    else Color.WHITE
                )

                // Clic en la tarjeta para seleccionarla y rellenar el formulario
                cardItemRoot.setOnClickListener {
                    onSelectClick(tarea)
                }

                // Clic en el botón lateral para moverla de lista al instante
                btnMover.setOnClickListener {
                    onMoverClick(tarea)
                }
            }
        }
    }
}

class TareaDiffCallback : DiffUtil.ItemCallback<Tarea>() {
    override fun areItemsTheSame(oldItem: Tarea, newItem: Tarea): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Tarea, newItem: Tarea): Boolean = oldItem == newItem
}