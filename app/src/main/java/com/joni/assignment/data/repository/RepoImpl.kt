
package com.joni.assignment.data.repository

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.joni.assignment.data.isNetworkAvailable
import com.joni.assignment.data.network.ApiService
import com.joni.assignment.data.offline.ProductDao
import com.joni.assignment.domain.Product
import com.joni.assignment.domain.ProductRepo
import com.joni.assignment.domain.ProductRequest
import com.joni.assignment.domain.ProductResponse
import javax.inject.Inject

class RepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val productDao : ProductDao,
   private val context: Context
) : ProductRepo {

    override suspend fun getProducts(): List<Product> {
        return if (isNetworkAvailable(context)) {
            // Fetch from the network if connected
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                val products = response.body()?.map {
                    Product(
                        product_name = it.product_name,
                        product_type = it.product_type,
                        price = it.price,
                        tax = it.tax,
                        image = it.image
                    )
                } ?: emptyList()

                // Save products to the local database
                productDao.insertAll(products)

                products
            } else {
                emptyList()
            }
        } else {
            // Fetch from the local database if offline
            productDao.getAllProducts()
        }
    }
    override suspend fun addProduct(product: ProductResponse): Result<ProductResponse> {
        return try {
            val response = apiService.addProduct(product)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to upload product"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
