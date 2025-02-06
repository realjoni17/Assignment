package com.joni.assignment.domain

data class ProductRequest(
    val product_type: String,
    val product_name: String,
    val price: Double,
    val tax: Double
)
