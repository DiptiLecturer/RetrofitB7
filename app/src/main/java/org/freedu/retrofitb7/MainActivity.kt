package org.freedu.retrofitb7

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.freedu.retrofitb7.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        setupListeners()
        setupObservers()
    }

    private fun setupListeners() {
        // 1. Swipe-to-Refresh Gesture Listener
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshProducts()
        }

        // 2. Retry Button Click Listener
        binding.btnRetry.setOnClickListener {
            viewModel.refreshProducts()
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            // Keep SwipeRefresh spinner visible, hide old error views
                            binding.swipeRefreshLayout.isRefreshing = true
                            binding.errorLayout.visibility = View.GONE
                        }
                        is UiState.Success -> {
                            // Stop refresh animation & render items
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.errorLayout.visibility = View.GONE
                            binding.recyclerView.visibility = View.VISIBLE
                            binding.recyclerView.adapter = ProductAdapter(state.data)
                        }
                        is UiState.Error -> {
                            // Stop refresh animation & display Error / Retry UI
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.recyclerView.visibility = View.GONE
                            binding.errorLayout.visibility = View.VISIBLE
                            binding.tvErrorMessage.text = state.message
                        }
                    }
                }
            }
        }
    }
}
@SuppressLint("ObsoleteSdkInt")
fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || activeNetwork.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        )
    } else {
        val networkInfo = connectivityManager.activeNetworkInfo ?: return false
        networkInfo.isConnected
    }
}
