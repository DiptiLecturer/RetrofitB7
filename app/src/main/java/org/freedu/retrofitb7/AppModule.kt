package org.freedu.retrofitb7

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    // 1. Provide Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Provide ApiService
    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    // 3. Provide Repository
    single { ProductRepository(get()) }

    // 4. Provide ViewModel (Koin manages ViewModel lifecycle)
    viewModel { ProductViewModel(get()) }
}