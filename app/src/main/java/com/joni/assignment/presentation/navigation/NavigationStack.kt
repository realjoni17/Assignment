package com.joni.assignment.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joni.assignment.presentation.MainScreen.MainScreen
import com.joni.assignment.presentation.MainViewModel
import com.joni.assignment.presentation.product.AddProduct

@Composable
fun NavigationStack(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(viewModel, navController)
        }
        composable(Screen.AddProduct.route) {
            AddProduct(modifier,viewModel)
        }
    }

}