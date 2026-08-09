package org.freedu.retrofitb7

import retrofit2.http.GET

interface ApiService {
    @GET("/products")
    suspend fun getProducts(): List<Product> // Directly returns List<Product>
}