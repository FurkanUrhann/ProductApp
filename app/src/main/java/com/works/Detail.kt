package com.works

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.works.data.Product


class Detail: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val product: Product? = intent.getParcelableExtra("product")
        val productTitleTextView: TextView = findViewById(R.id.Title)
        val productDescription: TextView = findViewById(R.id.Description)
        val productImageView: ImageView = findViewById(R.id.Image)

        if (product != null) {
            productTitleTextView.text = product.title
            productDescription.text = product.description


            Glide.with(this)
                .load(product.thumbnail)
                .override(1000, 900)
                .into(productImageView)
        }
    }
}