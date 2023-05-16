package com.example.product_list

import android.telecom.Call
import com.example.product_list.model.productlistItem
import retrofit2.http.GET

interface ApiInterface {
    @GET("/products")
    fun getDate(): retrofit2.Call<List<productlistItem>>
}




