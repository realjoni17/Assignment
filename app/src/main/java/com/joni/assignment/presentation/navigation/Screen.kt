package com.joni.assignment.presentation.navigation

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AddProduct : Screen("add_product")
}