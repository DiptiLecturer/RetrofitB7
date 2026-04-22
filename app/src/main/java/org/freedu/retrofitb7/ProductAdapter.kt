package org.freedu.retrofitb7

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.freedu.retrofitb7.databinding.ProductItemBinding



class ProductAdapter(private val list: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val product = list[position]

        holder.binding.productTitle.text = product.title
        holder.binding.productPrice.text = "$" + product.price

        Glide.with(holder.itemView.context)
            .load(product.image)
            .into(holder.binding.productImage)

    }
}