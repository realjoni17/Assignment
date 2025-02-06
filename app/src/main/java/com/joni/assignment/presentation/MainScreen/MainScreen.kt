

package com.joni.assignment.presentation.MainScreen


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.joni.assignment.presentation.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel,
               navController: NavController ,
               modifier: Modifier = Modifier) {
    val text = listOf( "Hello Swipe", "Welcome to the Assignment", )
    val products = viewModel.products.collectAsState(initial = emptyList()).value
    val errorMessage = viewModel.errorMessage.collectAsState(initial = null).value
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            SwipingTopAppBar(texts = text)},
        floatingActionButton = { FloatButton(modifier ,navController) })
            {  paddingvalues ->
                if (errorMessage != null) {
                    Text(text = errorMessage)
                } else if (products.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingvalues)) {
                        ProductList(products = products)
                    }
                }
            }
        }






