package com.joni.assignment.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joni.assignment.domain.Product
import com.joni.assignment.domain.ProductRepo
import com.joni.assignment.domain.ProductRequest
import com.joni.assignment.domain.ProductResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: ProductRepo) : ViewModel() {
   private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products : StateFlow<List<Product>> = _products

    private val _errorMesssage = MutableStateFlow<String?>(null)
    val errorMessage : StateFlow<String?> = _errorMesssage.asStateFlow()

    private val _productUploadStatus = MutableLiveData<Result<ProductResponse>>()
    val productUploadStatus: LiveData<Result<ProductResponse>> get() = _productUploadStatus


    init {
        loadProducts()

    }

    fun loadProducts(){
        viewModelScope.launch{
            try {
                val productList = repo.getProducts()
                _products.value = productList
                Log.d(TAG, "loadProducts: $productList")
            } catch (e: Exception){
                _errorMesssage.value = e.message
                Log.d(TAG, "loadProducts:${e.message}")
            }
        }
    }

    fun uploadProduct(product: ProductResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repo.addProduct(product)
            withContext(Dispatchers.Main) {
                _productUploadStatus.value = result
            }
        }
    }

}