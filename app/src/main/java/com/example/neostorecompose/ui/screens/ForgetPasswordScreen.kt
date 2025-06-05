package com.example.neostorecompose.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.navigation.NavHostController
import com.example.neostorecompose.ui.components.CustomTextField
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.theme.OrangeVariant
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    navHostController: NavHostController,
    userViewModel: UserViewModel
) {

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    val context = LocalContext.current
    var isButtonEnabled by remember { mutableStateOf(true) }
    var timerText by remember { mutableStateOf("") }

    val forgetPassword = userViewModel.forgetPasswordState.collectAsState().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Forgot Password",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { inner ->

        Box(
            modifier = Modifier
                .fillMaxSize()
//                .padding(inner)
                .background(OrangePrimary),
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
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 32.dp), // More balanced padding
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Enter your Email",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Email",
                        placeHolder = "Enter Email",
                        isError = emailError.isNotEmpty(),
                        errorMessage = emailError
                    )

                    LaunchedEffect(timerText) {
                        if (timerText.isNotEmpty()) {
                            kotlinx.coroutines.delay(1000L)
                            val secondsLeft = timerText.toInt() - 1
                            if (secondsLeft > 0) {
                                timerText = secondsLeft.toString()
                            } else {
                                timerText = ""
                                isButtonEnabled = true
                            }
                        }
                    }


                    Button(
                        onClick = {
                            var isValid = true

                            if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                emailError = "Enter a valid email"
                                isValid = false
                            } else {
                                emailError = ""
                            }

                            if (isValid) {
                                isButtonEnabled = false
                                timerText = "30" // 30 second countdown

                                userViewModel.forgetPassword(email)
                            }
                        },
                        enabled = isButtonEnabled,
                        colors = ButtonDefaults.buttonColors(OrangeVariant),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 8.dp)
                    ) {
                        Text(text = "Send", color = Color.White)
                    }

                    if (timerText.isNotEmpty()) {
                        Text(
                            text = "Resend available in ${timerText} sec",
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }


                }




            }

        }

        when(forgetPassword){
            is UiState.Error -> {
                Toast.makeText(context,forgetPassword.toString(), Toast.LENGTH_SHORT).show()
            }
            is UiState.Loading -> {
                LoaderComp()
            }
            is UiState.Success -> {
                Toast.makeText(context,"Password Reset Successfully", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }


    }
}