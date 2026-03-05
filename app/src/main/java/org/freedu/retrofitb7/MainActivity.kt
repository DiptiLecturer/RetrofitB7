package org.freedu.retrofitb7

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.freedu.retrofitb7.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(@Suppress("UNUSED_PARAMETER") savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        loadProducts()
        binding.fab.setOnClickListener {
        startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun loadProducts() {

        ApiClient.api.getProducts().enqueue(object : Callback<List<Product>> {

            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {

                if (response.isSuccessful) {

                    val list = response.body() ?: emptyList()

                    binding.recyclerView.adapter = ProductAdapter(list)

                }

            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {

                Toast.makeText(
                    this@MainActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()

            }

        })

    }
}