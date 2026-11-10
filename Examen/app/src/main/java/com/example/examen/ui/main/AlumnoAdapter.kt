package com.example.examen.ui.main


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.R
import com.example.examen.domain.model.Alumno

class AlumnoAdapter (
    val delAlumno : (Alumno) -> Unit,
): ListAdapter<Alumno, AlumnoAdapter.AlumnoViewHolder>(AlumnoDiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlumnoViewHolder {


        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alumno, parent, false)
        return AlumnoViewHolder({},vista)
    }

    override fun onBindViewHolder(
        holder: AlumnoViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    class AlumnoViewHolder(
       // private val binding: ItemAlumnoBinding,
        private val onClickDelete : (Alumno) -> Unit,
        itemview: View

        ) : RecyclerView.ViewHolder(itemview)
    {
        fun bind(alumno: Alumno) {
            val txt : TextView = itemView.findViewById(R.id.tvNombreAlumno)
            txt.text = alumno.nombre +" "+ alumno.apellido + " " + alumno.curso+ " " +  alumno.edad+ " " +alumno.correo
                itemView.setOnClickListener {

                    onClickDelete(alumno)
                }
            }
        }



        }








class AlumnoDiffCallback : DiffUtil.ItemCallback<Alumno>() {
    override fun areItemsTheSame(oldItem: Alumno, newItem: Alumno): Boolean =
        oldItem.nombre == newItem.nombre

    override fun areContentsTheSame(oldItem: Alumno, newItem: Alumno): Boolean =
        oldItem == newItem
}