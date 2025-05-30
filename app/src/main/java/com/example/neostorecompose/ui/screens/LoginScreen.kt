package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat.Style
import com.example.neostorecompose.R
import com.example.neostorecompose.domain.model.UserLoginRequest
import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.ui.components.ClickText
import com.example.neostorecompose.ui.components.CustomTextField
import com.example.neostorecompose.ui.components.SocialButton
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.theme.OrangeVariant
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    onLoginSuccess: () -> Unit,
    onClick: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState = userViewModel.userLoginState.collectAsState().value

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
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
                    .padding(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "NeoStore",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Welcome to NeoStore, Login to continue",
                    style = MaterialTheme.typography.bodySmall,
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


                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    placeHolder = "Enter Password",
                    isError = passwordError.isNotEmpty(),
                    errorMessage = passwordError
                )

                Button(
                    colors = ButtonDefaults.buttonColors(OrangeVariant),
                    onClick = {

                        var isValid = true

                        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                                .matches()
                        ) {
                            emailError = "Enter a valid email"
                            isValid = false
                        } else emailError = ""

                        if (password.length < 6) {
                            passwordError = "Password must be at least 6 characters"
                            isValid = false
                        } else passwordError = ""

                        if (isValid) {
                            val request = UserLoginRequest(
                                email = email,
                                password = password,
                            )
                            userViewModel.userLogin(request)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 8.dp)
                ) {
                    Text(text = "Login", color = Color.White)
                }
            }

            Text(
                text = "OR",
                color = Color.Gray,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(4.dp)){
                Text(
                    text = "Don't have an Account? ",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                )

                ClickText{
                    onClick()
                }

            }
            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                SocialButton(painterResource(R.drawable.search), "Google")
                SocialButton(painterResource(R.drawable.facebook), "Facebook")
                SocialButton(painterResource(R.drawable.yahoo), "Yahoo")
            }

    }

    when (loginState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)), // semi-transparent blur
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is UiState.Success -> {
            LaunchedEffect(Unit) {
                onLoginSuccess()
            }
        }

        is UiState.Error -> {
            Text(
                text = loginState.message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        else -> {

        }
    }
}
}