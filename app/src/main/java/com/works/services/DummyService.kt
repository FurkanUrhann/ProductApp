package com.works.services

import com.works.data.Product
import com.works.data.ProductData
import com.works.data.User
import com.works.data.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DummyService {

    @POST("/auth/login")
    fun login( @Body User: User): Call<UserData>

    @GET("products?limit=10")
    fun getProductdata():Call<ProductData>

    @GET("products/search")
    fun searchProducts(@Query("q") query: String):Call<ProductData>

}