package org.freedu.retrofitb7

class ProductRepository(private val apiService: ApiService) {
    suspend fun fetchProducts(): List<Product> {
        return apiService.getProducts()
    }
}