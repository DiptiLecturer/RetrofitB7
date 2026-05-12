package org.freedu.retrofitb7


import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    //HTTP Method
    @GET("/products")//Just to show the data
    fun getProducts(): Call<List<Product>>

    //Post,Put,Delete,Patch

}