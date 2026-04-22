package org.freedu.retrofitb7


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("/products")
    fun getProducts(): Call<List<Product>>

}