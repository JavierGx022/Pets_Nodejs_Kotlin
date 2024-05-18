package com.componentes.frontend.View

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.componentes.frontend.Model.Pet
import com.componentes.frontend.R
import com.componentes.frontend.ViewModel.PetViewModel
import com.componentes.frontend.databinding.ActivityAddPetBinding
import com.componentes.frontend.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class AddPetActivity : AppCompatActivity() {
    private val petViewModel: PetViewModel by viewModels()
    private lateinit var binding: ActivityAddPetBinding

    private var selectedImageFile: File? = null
    private val PICK_IMAGE_REQUEST = 1
    private var typeS=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val items = listOf("dog", "cat", "other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Aplicar el adaptador al Spinner
        binding.spTypePet.adapter = adapter
        binding.spTypePet.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?,
                view: View?, position: Int, id: Long
            ) {
                typeS =parent?.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnPhoto.setOnClickListener {
            openImageChooser()
        }

        binding.btnSave.setOnClickListener {
            submitPet()
        }

        binding.btnCancel.setOnClickListener {
            btnCancel()
        }

        petViewModel.statusMessage.observe(this, Observer { message ->

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if(message.equals("Pet stored successfully")){
                val intent=Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

    }

    private fun btnCancel (){
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openImageChooser() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                selectedImageFile = File(getRealPathFromURI(selectedImageUri))
            }
        }
    }

    private fun getRealPathFromURI(uri: Uri): String {
        var result = ""
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            if (idx != -1) {
                result = cursor.getString(idx)
            }
            cursor.close()
        }
        return result
    }

    private fun submitPet() {
        val type = typeS
        Log.e("TAG", "el type seleccionado es: $typeS")
        val name = binding.txtName.text.toString()
        val age = binding.txtAge.text.toString().toInt()
        val breed = binding.txtBreed.text.toString()
        val image = selectedImageFile

        if (type.isEmpty() || name.isEmpty() || age == null || breed.isEmpty() || image == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show()
            return
        }

        petViewModel.storePet(type, name, age, breed, image)
    }

}

