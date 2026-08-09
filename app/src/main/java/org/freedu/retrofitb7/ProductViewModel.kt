package org.freedu.retrofitb7

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productsState = MutableLiveData<UiState<List<Product>>>()
    val productsState: LiveData<UiState<List<Product>>> = _productsState

    fun loadProducts() {
        _productsState.value = UiState.Loading

        // Launch coroutine bound to ViewModel's lifecycle
        viewModelScope.launch {
            try {
                val products = repository.fetchProducts()
                _productsState.value = UiState.Success(products)
            } catch (e: Exception) {
                _productsState.value = UiState.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }
}