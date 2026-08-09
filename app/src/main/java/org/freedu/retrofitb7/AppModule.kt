package org.freedu.retrofitb7

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // Provide Retrofit Instance
    single {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provide ApiService
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }
}