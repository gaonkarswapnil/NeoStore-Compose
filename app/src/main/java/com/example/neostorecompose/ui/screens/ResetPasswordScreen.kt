package com.example.neostorecompose.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.neostorecompose.domain.model.UserLoginRequest
import com.example.neostorecompose.ui.components.CustomTextField
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.theme.OrangeVariant
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {

    val token = userViewModel.getAccessToken()
    val changePassRes = userViewModel.changePassRes.collectAsState().value

    var oldpassword by remember { mutableStateOf("") }
    var newpassword by remember { mutableStateOf("") }
    var confirmpassword by remember { mutableStateOf("") }

    var oldpasswordError by remember { mutableStateOf("") }
    var newpasswordError by remember { mutableStateOf("") }
    var confirmpasswordError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Reset Password",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = OrangePrimary
                )
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(OrangePrimary)
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    bottom = innerPadding.calculateBottomPadding()
                ),
            contentAlignment = Alignment.Center
        ) {


            Card(
                shape = RoundedCornerShape(26.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(16.dp),
                ) {

                    CustomTextField(
                        value = oldpassword,
                        onValueChange = { oldpassword = it },
                        label = "Old Password",
                        isPassword = true,
                        placeHolder = "Enter Password",
                        isError = oldpasswordError.isNotEmpty(),
                        errorMessage = oldpasswordError
                    )
                    CustomTextField(
                        value = newpassword,
                        onValueChange = { newpassword = it },
                        label = "new Password",
                        isPassword = true,
                        placeHolder = "Enter new Password",
                        isError = newpasswordError.isNotEmpty(),
                        errorMessage = newpasswordError
                    )

                    CustomTextField(
                        value = confirmpassword,
                        onValueChange = { confirmpassword = it },
                        label = "confirm Password",
                        placeHolder = "Enter confirm Password",
                        isPassword = true,
                        isError = confirmpasswordError.isNotEmpty(),
                        errorMessage = confirmpasswordError
                    )


                    Button(
                        colors = ButtonDefaults.buttonColors(OrangeVariant),
                        onClick = {
                            var isValid = true

                            if (oldpassword.isBlank()) {
                                isValid = false
                                oldpasswordError = "Old password is required"
                            }

                            if (newpassword.length < 6) {
                                isValid = false
                                newpasswordError = "Password must be at least 6 characters"
                            }

                            if (confirmpassword.length < 6) {
                                isValid = false
                                confirmpasswordError =
                                    "Password must be at least 6 characters"
                            }

                            if (newpassword != confirmpassword) {
                                isValid = false
                                newpasswordError = "Passwords do not match"
                                confirmpasswordError = "Passwords do not match"
                            }

                            if (isValid && token != null) {
                                userViewModel.changePassword(
                                    token = token,
                                    old = oldpassword,
                                    pass = newpassword,
                                    confirmpass = confirmpassword
                                )
                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "Submit", color = Color.White)
                    }
                }


            }

        }


        when (changePassRes) {
            is UiState.Loading -> {
                LoaderComp()
            }

            is UiState.Success -> {
                val statusCode = changePassRes.data.status
                if(statusCode==200){
                    Toast.makeText(LocalContext.current, "Successfully updated password", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }

            else -> {}
        }


    }

}
