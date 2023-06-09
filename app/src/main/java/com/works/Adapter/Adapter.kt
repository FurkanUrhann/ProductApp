package com.works.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import android.content.Context
import android.content.Intent
import com.works.Detail
import com.works.R
import com.works.data.Product


class Adapter(
    private val context: Context,
    private val productList: List<Product>
) : RecyclerView.Adapter<Adapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.TextView)
        val productImage: ImageView = itemView.findViewById(R.id.imageview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.productName.text = product.title

        Glide.with(context)
            .load(product.thumbnail)
            .override(200, 100)
            .centerCrop()
            .into(holder.productImage)


        holder.itemView.setOnClickListener {
            val intent = Intent(context, Detail::class.java)
            intent.putExtra("product", product)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}