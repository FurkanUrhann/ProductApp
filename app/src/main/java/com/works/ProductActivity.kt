package com.works

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.works.Adapter.Adapter
import com.works.configs.ApiClient
import com.works.data.Product
import com.works.data.ProductData
import com.works.services.DummyService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class ProductActivity : AppCompatActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var productRecyclerView: RecyclerView
    private lateinit var dummyService: DummyService
    lateinit var customAdapter: Adapter
    private var productList = mutableListOf<Product>()
    private var filteredProductList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        searchEditText = findViewById(R.id.search)
        productRecyclerView = findViewById(R.id.RecyclerView)
        productRecyclerView.layoutManager = LinearLayoutManager(this)

        dummyService = ApiClient.getClient().create(DummyService::class.java)
        dummyService.getProductdata().enqueue(object : Callback<ProductData> {
            override fun onResponse(call: Call<ProductData>, response: Response<ProductData>) {
                Log.d("products", response.body().toString())

                for (product in response.body()!!.products) {
                    productList.add(product)
                }

                filteredProductList = productList.toMutableList()
                updateRecyclerView(filteredProductList)
            }

            override fun onFailure(call: Call<ProductData>, t: Throwable) {
                Log.e("eroor", "server error")
            }
        })
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val searchText = s.toString().trim().toLowerCase(Locale.getDefault())

                filteredProductList = if (searchText.isEmpty()) {
                    productList.toMutableList()
                } else {
                    productList.filter { product ->
                        product.title.toLowerCase(Locale.getDefault()).contains(searchText)
                    }.toMutableList()
                }

                updateRecyclerView(filteredProductList)
            }
        })
    }

    private fun updateRecyclerView(newProductList: List<Product>) {
        customAdapter = Adapter(this@ProductActivity, newProductList)
        productRecyclerView.adapter = customAdapter
    }
}