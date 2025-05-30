    package com.example.neostorecompose.ui.navigation

    import android.widget.Toast
    import androidx.compose.runtime.Composable
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavHostController
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import com.example.neostorecompose.ui.screens.LoginScreen
    import com.example.neostorecompose.ui.screens.RegisterScreen
    import com.example.neostorecompose.ui.viewmodel.UserViewModel
    import androidx.compose.ui.platform.LocalContext

    @Composable
    fun SetUpNav(navHostController: NavHostController) {

        val userViewModel: UserViewModel = hiltViewModel()
        val context = LocalContext.current

        NavHost(
            navController = navHostController,
            startDestination = Screens.Login.route
        ) {

            composable(Screens.Register.route) {
                RegisterScreen(
                    userViewModel = userViewModel,
                    onRegisterSucess = {
                        navHostController.navigate(Screens.Login.route)
                    }
                )
            }

            composable(Screens.Login.route){
                LoginScreen(
                    userViewModel = userViewModel,
                    onLoginSuccess = {

                    },
                    onClick = {
                        navHostController.navigate(Screens.Register.route)
                    }
                )
            }

        }

    }