package com.joni.assignment.presentation.product

import android.graphics.Color
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.joni.assignment.domain.ProductResponse
import com.joni.assignment.presentation.MainViewModel
import kotlinx.serialization.Serializable


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProduct(modifier: Modifier = Modifier, viewModel: MainViewModel) {
     val context = LocalContext.current
    val productTypes = listOf("Electronics", "Clothing", "Food", "Books")
    var selectedType by remember { mutableStateOf(productTypes.first()) }
    var productName by remember { mutableStateOf("") }
    var sellingPrice by remember { mutableStateOf("") }
    var taxRate by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf("") }
    val imagePickerLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        if (uri != null){
            imageUri = uri }}
    val productUploadStatus by viewModel.productUploadStatus.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Product") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (validateFields(productName, sellingPrice, taxRate)) {
                    isLoading = true
                    viewModel.uploadProduct(
                        ProductResponse(
                            name = productName,
                            price = sellingPrice.toDouble(),
                            tax = taxRate.toDouble(),
                            type = selectedType,
                         //   message = null
                        )
                    )
                } else {
                    Toast.makeText(context, "Please fill all fields correctly.", Toast.LENGTH_SHORT)
                        .show()
                }
            }) {
                Icon(Icons.Default.Check, contentDescription = "Submit Product")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Type Dropdown
            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {}
            ) {
                Text(text = "Product Type:", style = MaterialTheme.typography.bodyMedium)
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(onClick = { /* Expand Dropdown */ }) {
                        Text(selectedType)
                    }
                }
            }

            // Product Name Input
            OutlinedTextField(
                value = productName,
                onValueChange = { productName = it },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Selling Price Input
            OutlinedTextField(
                value = sellingPrice,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) sellingPrice = it },
                label = { Text("Selling Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Tax Rate Input
            OutlinedTextField(
                value = taxRate,
                onValueChange = { if (it.all { c -> c.isDigit() || c == '.' }) taxRate = it },
                label = { Text("Tax Rate (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            // Image Selection Button
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Select Image")
            }

            // Image Preview
            imageUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)

                )
            }

            // Progress Indicator
            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }

    // Success/Error Dialog
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Product Upload") },
            text = { Text(successMessage) },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
    productUploadStatus?.let {
        when {
            it.isSuccess -> {
                Toast.makeText(context, "Product uploaded successfully!", Toast.LENGTH_SHORT).show()

            }
            it.isFailure -> {
                Toast.makeText(context, "Failed to upload product.", Toast.LENGTH_SHORT).show()

            }
            else -> {
                Toast.makeText(context, "Uploading...", Toast.LENGTH_SHORT).show()

            }
        }
    }
}



private fun validateFields(name: String, price: String, tax: String): Boolean {
    return name.isNotBlank() && price.toDoubleOrNull() != null && tax.toDoubleOrNull() != null
}
