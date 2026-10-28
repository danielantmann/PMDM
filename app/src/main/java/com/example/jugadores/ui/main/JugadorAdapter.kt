package com.example.jugadores.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.jugadores.databinding.ItemJugadorBinding
import com.example.jugadores.domain.model.Jugador

class JugadorAdapter(
    private val onClickDelete: (Jugador) -> Unit
) : ListAdapter<Jugador, JugadorAdapter.JugadorViewHolder>(JugadorDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadorViewHolder {
        val binding = ItemJugadorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JugadorViewHolder(binding, onClickDelete)
    }

    override fun onBindViewHolder(holder: JugadorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // ¡Ojo aquí! Debe llamarse JugadorViewHolder exactamente
    class JugadorViewHolder(
        private val binding: ItemJugadorBinding,
        private val onClickDelete: (Jugador) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(jugador: Jugador) {
            with(binding) {
                tvNombreJugador.text = jugador.nombre

                root.setOnClickListener {
                    onClickDelete(jugador)
                }
            }
        }
    }
}

class JugadorDiffCallback : DiffUtil.ItemCallback<Jugador>() {
    override fun areItemsTheSame(oldItem: Jugador, newItem: Jugador): Boolean =
        oldItem.nombre == newItem.nombre // O oldItem.id == newItem.id si tienes ID

    override fun areContentsTheSame(oldItem: Jugador, newItem: Jugador): Boolean =
        oldItem == newItem
}