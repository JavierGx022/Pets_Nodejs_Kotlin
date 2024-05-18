package com.componentes.recyclerviewexample.AdapterRecycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.componentes.frontend.Model.Pet
import com.componentes.frontend.R

class PetViewHolder(view:View): RecyclerView.ViewHolder(view) {
    val name= view.findViewById<TextView>(R.id.tvNombre)
    val type= view.findViewById<TextView>(R.id.tvType)
    val breed= view.findViewById<TextView>(R.id.tvBreed)


    fun render(pet:Pet, onClickListener: (Pet)->Unit){
        name.text= pet.name
        type.text= "Type: ${pet.type}"
        breed.text= "Breed: ${pet.breed}"

        itemView.setOnClickListener{
            onClickListener(pet)
        }
    }
}