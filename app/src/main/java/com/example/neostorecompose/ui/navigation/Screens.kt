package com.example.neostorecompose.ui.navigation

sealed class Screens(val route: String) {
    object Register: Screens("register")
    object Login: Screens("login")

    object goToProductList :Screens("productList/{categoryId}")
    object goToProductDetails : Screens("productDetails/{productId}") {
        fun passProductId(productId: Int) = "productDetails/$productId"
    }

    object ProfileScreen : Screens("userProfileScreen")
    object EditProfileScreen : Screens("editProfileScreen")

    object AddressListScreen : Screens("addressListScreen")
    object AddAddressScreen : Screens("addAddressScreen")


    object OrderListScreen : Screens("orderListScreen")
    object OrderDetailsScreen :Screens("fetchOrderDetails/{orderId}")
    object ForgetPasswordScreen :Screens("forgetPassword")



}