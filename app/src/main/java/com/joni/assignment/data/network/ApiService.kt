
package com.joni.assignment.data.network

import com.joni.assignment.data.network.dto.ProductDTOItem
import com.joni.assignment.domain.ProductRequest
import com.joni.assignment.domain.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("/api/public/get")
    suspend fun getProducts(): Response<List<ProductDTOItem>>

    @POST("/api/public/add")
    suspend fun addProduct(@Body product: ProductResponse): Response<ProductResponse>
}
