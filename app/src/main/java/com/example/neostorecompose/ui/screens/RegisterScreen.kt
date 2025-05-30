package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.neostorecompose.R
import com.example.neostorecompose.domain.model.request.UserRegistrationRequest
import com.example.neostorecompose.ui.components.GenderRadioButton
import com.example.neostorecompose.ui.components.CustomTextField
import com.example.neostorecompose.ui.components.SocialButton
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.theme.OrangeVariant
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@Composable
fun RegisterScreen(
    userViewModel: UserViewModel,
    onRegisterSucess: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("Male") }
    val registerState = userViewModel.userState.collectAsState().value


    var firstNameError by remember { mutableStateOf("") }
    var lastNameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }


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
                    text="NeoStore",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier=Modifier.align(Alignment.CenterHorizontally)
                )

                Text(
                    text="Welcome to NeoStore, please register",
                    style = MaterialTheme.typography.bodySmall,
                    modifier=Modifier.align(Alignment.CenterHorizontally)
                )


                CustomTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = "First Name",
                    placeHolder = "Enter your first name",
                    isError = firstNameError.isNotEmpty(),
                    errorMessage = firstNameError
                )

                CustomTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = "Last Name",
                    placeHolder = "Enter your last name",
                    isError = lastNameError.isNotEmpty(),
                    errorMessage = lastNameError
                )

                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    placeHolder = "Enter your email",
                    isError = emailError.isNotEmpty(),
                    errorMessage = emailError
                )

                CustomTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    placeHolder = "Enter your password",
                    isPassword = true,
                    isError = passwordError.isNotEmpty(),
                    errorMessage = passwordError
                )

                CustomTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm Password",
                    placeHolder = "Confirm your password",
                    isPassword = true,
                    isError = confirmPasswordError.isNotEmpty(),
                    errorMessage = confirmPasswordError
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    GenderRadioButton("Male", selectedGender) { selectedGender = it }
                    GenderRadioButton("Female", selectedGender) { selectedGender = it }
                }

                CustomTextField(
                    value = phoneNo,
                    onValueChange = { phoneNo = it },
                    label = "Phone No",
                    placeHolder = "Enter your phone number",
                    isError = phoneError.isNotEmpty(),
                    errorMessage = phoneError
                )

                Button(
                    colors = ButtonDefaults.buttonColors(OrangeVariant),
                    onClick = {

                        var isValid = true

                        if (firstName.isBlank()) {
                            firstNameError = "First name is required"
                            isValid = false
                        } else firstNameError = ""

                        if (lastName.isBlank()) {
                            lastNameError = "Last name is required"
                            isValid = false
                        } else lastNameError = ""

                        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            emailError = "Enter a valid email"
                            isValid = false
                        } else emailError = ""

                        if (password.length < 6) {
                            passwordError = "Password must be at least 6 characters"
                            isValid = false
                        } else passwordError = ""

                        if (confirmPassword != password) {
                            confirmPasswordError = "Passwords do not match"
                            isValid = false
                        } else confirmPasswordError = ""

                        if (phoneNo.length != 10 || !phoneNo.all { it.isDigit() }) {
                            phoneError = "Enter a valid 10-digit phone number"
                            isValid = false
                        } else phoneError = ""


                        if (isValid) {
                            val request = UserRegistrationRequest(
                                confirmPassword = confirmPassword,
                                email = email,
                                firstName = firstName,
                                gender = selectedGender,
                                lastName = lastName,
                                password = password,
                                phoneNo = phoneNo.toLong()
                            )
                            userViewModel.userRegistration(request)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 8.dp)
                ) {
                    Text(text = "Register", color = Color.White)
                }

                // Divider
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(0.8f)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    SocialButton(painterResource(R.drawable.search),"Google")
                    SocialButton(painterResource(R.drawable.facebook), "Facebook")
                    SocialButton(painterResource(R.drawable.yahoo), "Yahoo")
                }
            }
        }

        when(registerState){
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
                    onRegisterSucess()
                }
            }
            is UiState.Error -> {
                Text(
                    text = registerState.message,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            else -> {

            }
        }

    }
}