package com.componentes.frontend.Network

import com.componentes.frontend.Model.Pet
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface PetsApi {
    @Multipart
    @POST("/pets")
    fun storePet(
        @Part("type") type: RequestBody,
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("breed") breed: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("/pets")
    suspend fun getAllPets(): List<Pet>

    @GET("/pets/filter")
    suspend fun filterPets(
        @Query("name") name: String?,
        @Query("sortBy") sortBy: String?
    ): List<Pet>
}