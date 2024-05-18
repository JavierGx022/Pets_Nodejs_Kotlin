package com.componentes.frontend.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.componentes.frontend.R
import com.componentes.frontend.databinding.ActivityDetailsPetBinding


class DetailsPetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailsPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvNamePet.text= intent.getStringExtra("name")
        binding.tvAgePet.text= intent.getStringExtra("age")
        binding.tvTypePet.text= intent.getStringExtra("type")
        binding.tvBreedPet.text= intent.getStringExtra("breed")

        val imageUrl = intent.getStringExtra("image").toString()
        Log.e("URL IMG", "la url es: ${imageUrl}")

        // Cargar la imagen usando Glide
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivPet)

        binding.btnGoList.setOnClickListener {
            goToList()
        }

    }

    private fun goToList (){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}