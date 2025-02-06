
package com.joni.assignment.domain

interface ProductRepo {
    suspend fun getProducts(): List<Product>

   suspend fun addProduct(product: ProductResponse) : Result<ProductResponse>
}
