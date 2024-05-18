package com.componentes.frontend.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.componentes.frontend.Model.Pet
import com.componentes.frontend.ViewModel.PetViewModel
import com.componentes.frontend.databinding.ActivityMainBinding
import com.componentes.recyclerviewexample.AdapterRecycler.PetAdapter
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private val petViewModel: PetViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var fieldS=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecycler()

        val items = listOf("Type", "Breed", "Age")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Aplicar el adaptador al Spinner
        binding.spOrderby.adapter = adapter

        binding.spOrderby.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                                        view: View?, position: Int, id: Long
            ) {
                 fieldS=parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnAdd.setOnClickListener {
            goAddPet()
        }

        binding.btnFilter.setOnClickListener {
            var name=binding.txtFindPet.text.toString()
            var field= fieldS
            petViewModel.filterPets(name, field)
        }


        petViewModel.pets.observe(this, Observer { pets ->
            (binding.rvPets.adapter as PetAdapter).updatePets(pets)
        })

    }

    private fun goAddPet(){
        val intent= Intent(this, AddPetActivity::class.java)
        startActivity(intent)
    }



    //iniciar recyclerView con todas las mascotas
    private fun initRecycler(){
        binding.rvPets.layoutManager= LinearLayoutManager(this)
        petViewModel.pets.observe(this, Observer { pets ->
            binding.rvPets.adapter = PetAdapter(pets){p->
                onItemSelected(
                    p
                )
            }
        })
        petViewModel.getAllPets()

    }

    private fun onItemSelected(pet: Pet){
        val intent= Intent(this, DetailsPetActivity::class.java)
        intent.putExtra("name", pet.name)
        intent.putExtra("type", pet.type)
        intent.putExtra("breed", pet.breed)
        intent.putExtra("age", pet.age)
        intent.putExtra("image", pet.image)
        startActivity(intent)
    }


    private fun spinnerField(){
        val items = listOf("Type", "Breed", "Age")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Aplicar el adaptador al Spinner
        binding.spOrderby.adapter = adapter

    }

}