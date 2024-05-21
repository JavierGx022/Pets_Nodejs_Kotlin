package com.componentes.frontend.ViewModel

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.componentes.frontend.Model.Pet
import com.componentes.frontend.Repositories.PetsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class PetViewModel : ViewModel() {
    private val petsRepository = PetsRepository()

    private val _pets = MutableLiveData<List<Pet>>()
    val pets: LiveData<List<Pet>> = _pets

    private val _statusMessage = MutableLiveData<String>()
    val statusMessage: LiveData<String> get() = _statusMessage




    fun storePet(type: String, name: String, age: Int, breed: String, image: File) {
        viewModelScope.launch {
            val call = petsRepository.storePet(type, name, age, breed, image)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        _statusMessage.postValue("Pet stored successfully")
                    } else {
                        _statusMessage.postValue("Failed to store pet")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    _statusMessage.postValue(t.message)
                }
            })
        }
    }

    fun getAllPets() {
        viewModelScope.launch {
            try {
                val petsList = petsRepository.getPets()
                if (petsList.isNotEmpty()) {
                    _pets.postValue(petsList)
                } else {
                    Log.e("ERROR LISTA VACIO", "LA LISTA DE PETS ESTA VACIA")
                }
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun filterPets(name: String?, sortBy: String?) {
        Log.d("PetViewModel", "Filtering pets by name: $name and sorting by: $sortBy")
        viewModelScope.launch {
            try {
                val filteredPets = petsRepository.filterPets(name, sortBy)
                withContext(Dispatchers.Main) {
                    _pets.value = filteredPets
                }
            } catch (e: Exception) {
                Log.e("PetViewModel", "Error filtering pets: ${e.message}")

            }
        }
    }



}
