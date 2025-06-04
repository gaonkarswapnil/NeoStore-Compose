package com.example.neostorecompose.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.neostorecompose.ui.screens.LoginScreen
import com.example.neostorecompose.ui.screens.RegisterScreen
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.neostorecompose.ui.components.BottomNavigationBar
import com.example.neostorecompose.ui.screens.AddAddressScreen
import com.example.neostorecompose.ui.screens.AddressListScreen
import com.example.neostorecompose.ui.screens.CartListScreen
import com.example.neostorecompose.ui.screens.DashboardScreen
import com.example.neostorecompose.ui.screens.ProductDetailsScreen
import com.example.neostorecompose.ui.screens.EditProfileScreen
import com.example.neostorecompose.ui.screens.OrderListScreen
import com.example.neostorecompose.ui.screens.ProductListScreen
import com.example.neostorecompose.ui.viewmodel.ProductViewModel
import com.example.neostorecompose.ui.screens.ProfileScreen
import com.example.neostorecompose.ui.screens.UserProfileDataScreen
import com.example.neostorecompose.ui.viewmodel.AddressViewModel
import com.example.neostorecompose.ui.viewmodel.CartViewModel
import com.example.neostorecompose.ui.viewmodel.DashboardViewModel
import com.example.neostorecompose.ui.viewmodel.OrderViewModel

@Composable
fun SetUpNav(navHostController: NavHostController) {

    val userViewModel: UserViewModel = hiltViewModel()
    val productViewModel: ProductViewModel = hiltViewModel()
    val addressViewModel: AddressViewModel = hiltViewModel()
    val orderViewModel : OrderViewModel = hiltViewModel()

    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val cartViewModel: CartViewModel = hiltViewModel()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        SealedBottomNavItem.dashboard.route,
        SealedBottomNavItem.search.route,
        SealedBottomNavItem.cart.route,
        SealedBottomNavItem.userprofile.route,
    )


    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController = navHostController)
            }
        }
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screens.Login.route,
            modifier = Modifier.padding(it)
        ) {

            composable(Screens.Register.route) {
                RegisterScreen(
                    userViewModel = userViewModel,
                    onRegisterSucess = {
                        navHostController.navigate(Screens.Login.route)
                    }
                )
            }

            composable(Screens.Login.route) {
                LoginScreen(
                    userViewModel = userViewModel,
                    onLoginSuccess = {
                        navHostController.navigate(SealedBottomNavItem.dashboard.route) {
                            popUpTo("login") { inclusive = true }
                        }
                    },
                    onClick = {
                        navHostController.navigate(Screens.Register.route)
                    }
                )
            }

            composable(SealedBottomNavItem.dashboard.route) {
                DashboardScreen(navHostController)
            }
            composable(SealedBottomNavItem.userprofile.route) {
                ProfileScreen(userViewModel, dashboardViewModel, navHostController)
            }

            composable(SealedBottomNavItem.cart.route) {
//                CartListScreen(userViewModel, cartViewModel)
                CartListScreen(
                    navHostController, userViewModel, cartViewModel, addressViewModel,
                    provideAddress = {addressList->
                        if(addressList.isEmpty()){
                            navHostController.navigate(Screens.AddAddressScreen.route)
                        }else{
                            navHostController.navigate(Screens.AddressListScreen.route)
                        }
                    })
            }

            composable(
                Screens.goToProductList.route, arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType }
            )) { navBackStackEntry ->
                val categoryId = navBackStackEntry.arguments?.getInt("categoryId") ?: 0

                ProductListScreen(
                    productViewModel = productViewModel,
                    categoryId = categoryId,
                    navController = navHostController
                )

            }
            composable(
                Screens.goToProductDetails.route, arguments = listOf(
                navArgument("productId") { type = NavType.IntType }
            )) { navBackStackEntry ->
                val productId = navBackStackEntry.arguments?.getInt("productId") ?: 0

                ProductDetailsScreen(
                    productViewModel = productViewModel,
                    productId = productId
                )

            }


            composable(Screens.ProfileScreen.route) {
                UserProfileDataScreen(dashboardViewModel, userViewModel, navHostController)
            }

            composable(Screens.EditProfileScreen.route) {
                EditProfileScreen(navHostController, userViewModel, dashboardViewModel)
            }


            composable(Screens.AddAddressScreen.route) {
                AddAddressScreen(navHostController, addressViewModel)
            }
            composable(Screens.AddressListScreen.route) {
                AddressListScreen(navHostController, addressViewModel, orderViewModel, userViewModel)
            }

            composable(Screens.OrderListScreen.route){
                OrderListScreen()
            }
        }

    }
}