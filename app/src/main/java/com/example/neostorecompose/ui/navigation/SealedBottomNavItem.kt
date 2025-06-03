package com.example.neostorecompose.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.neostorecompose.R

sealed class SealedBottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object dashboard : SealedBottomNavItem("dashboard", Icons.Default.Home, "Dashboard")
    object search : SealedBottomNavItem("search",Icons.Default.Search, "Search")
    object cart : SealedBottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
    object userprofile : SealedBottomNavItem("userProfile", Icons.Default.AccountCircle, "Profile")
}