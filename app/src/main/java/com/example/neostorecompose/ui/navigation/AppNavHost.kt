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
import com.example.neostorecompose.ui.screens.DashboardScreen
import com.example.neostorecompose.ui.screens.ProductListScreen
import com.example.neostorecompose.ui.viewmodel.ProductViewModel

@Composable
fun SetUpNav(navHostController: NavHostController) {

    val userViewModel: UserViewModel = hiltViewModel()
    val productViewModel: ProductViewModel = hiltViewModel()

    val context = LocalContext.current

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

            composable(Screens.goToProductList.route, arguments = listOf(
                navArgument("categoryId") { type = NavType.IntType }
            )) { navBackStackEntry ->
               val categoryId =navBackStackEntry.arguments?.getInt("categoryId") ?: 0

                ProductListScreen(
                    productViewModel = productViewModel,
                    categoryId = categoryId
                )

            }

        }

    }
}