package com.example.palas.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.palas.databinding.ItemPalaBinding
import com.example.palas.domain.model.Pala


class PalaAdapter(
    private val onClickDelete: (Pala) -> Unit
): ListAdapter<Pala, PalaAdapter.PalaViewHolder>((PalaDiffCallback())) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PalaViewHolder {
        val binding = ItemPalaBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return PalaViewHolder(binding, onClickDelete)
    }

    override fun onBindViewHolder(holder: PalaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PalaViewHolder(
        private val binding: ItemPalaBinding,
        private val onClickDelete: (Pala) -> Unit,
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(pala: Pala) {
            with(binding) {
                tvPalaNombre.text = pala.nombre

                root.setOnClickListener {
                    onClickDelete(pala)
                }
            }
        }
    }
}

class PalaDiffCallback : DiffUtil.ItemCallback<Pala>() {
    override fun areItemsTheSame(oldItem: Pala, newItem: Pala): Boolean =
        oldItem.nombre == newItem.nombre // O oldItem.id == newItem.id si tienes ID

    override fun areContentsTheSame(oldItem: Pala, newItem: Pala): Boolean =
        oldItem == newItem
}