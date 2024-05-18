package com.componentes.recyclerviewexample.AdapterRecycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.componentes.frontend.Model.Pet
import com.componentes.frontend.R

class PetAdapter(private var petsList: List<Pet>, private val onClickListener: (Pet)->Unit) : RecyclerView.Adapter<PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_row, parent, false)
        return PetViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return petsList.size
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val item = petsList[position]
        holder.render(item, onClickListener)
    }

    fun updatePets(newPets: List<Pet>) {
        petsList = newPets
        notifyDataSetChanged()
    }
}

