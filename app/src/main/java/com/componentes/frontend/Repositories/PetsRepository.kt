package com.componentes.frontend.Repositories

import com.componentes.frontend.Model.Pet
import com.componentes.frontend.Network.PetsApi
import com.componentes.frontend.Network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PetsRepository {
    private val petsApi: PetsApi = RetrofitClient.petsApi

    suspend fun getPets(): List<Pet> {
        return petsApi.getAllPets()
    }

    fun storePet(type: String, name: String, age: Int, breed: String, image: File): Call<ResponseBody> {
        val typeBody = type.toRequestBody("text/plain".toMediaTypeOrNull())
        val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
        val ageBody = age.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val breedBody = breed.toRequestBody("text/plain".toMediaTypeOrNull())
        val imageBody = MultipartBody.Part.createFormData(
            "image", image.name, image.asRequestBody("image/*".toMediaTypeOrNull())
        )

        return petsApi.storePet(typeBody, nameBody, ageBody, breedBody, imageBody)
    }

    suspend fun filterPets(name: String?, sortBy: String?): List<Pet> {
        return withContext(Dispatchers.IO) {
            petsApi.filterPets(name, sortBy)
        }
    }
}