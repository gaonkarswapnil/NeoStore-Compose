package com.example.neostorecompose.ui.navigation

sealed class Screens(val route: String) {
    object Register: Screens("register")
    object Login: Screens("login")

    object goToProductList :Screens("productList/{categoryId}")

    object ProfileScreen : Screens("userProfileScreen")
    object EditProfileScreen : Screens("editProfileScreen")

}