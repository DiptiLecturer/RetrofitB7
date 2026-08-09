package org.freedu.retrofitb7

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _productsState = MutableStateFlow<UiState<List<Product>>>(UiState.Loading)
    val productsState: StateFlow<UiState<List<Product>>> = _productsState.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _productsState.value = UiState.Loading
            try {
                val products = repository.fetchProducts()
                _productsState.value = UiState.Success(products)
            } catch (e: Exception) {
                _productsState.value = UiState.Error(e.localizedMessage ?: "An unexpected error occurred")
            }
        }
    }

    // Explicitly used for pull-to-refresh & retry button
    fun refreshProducts() {
        loadProducts()
    }
}