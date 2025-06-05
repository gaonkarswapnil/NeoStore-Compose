package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.neostorecompose.R
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.components.ProfileScreenUserDataComp
import com.example.neostorecompose.ui.navigation.Screens
import com.example.neostorecompose.ui.navigation.SealedBottomNavItem
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.theme.OrangeVariant
import com.example.neostorecompose.ui.viewmodel.DashboardViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileDataScreen(
    dashBoardViewModel: DashboardViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {

    val accessToken = userViewModel.getAccessToken() ?: ""
    val userProfileData = dashBoardViewModel.dashboardRes.collectAsState().value

    LaunchedEffect(accessToken) {
        if (accessToken.isNotEmpty()) {
            dashBoardViewModel.getDashboard(accessToken)
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Profile",
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
//                        navController.navigate(Screens.ProfileScreen.route) {
//                            popUpTo(SealedBottomNavItem.userprofile.route) { inclusive = true }
//                        }
                        navController.popBackStack()
                    }) {
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
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        navController.navigate(Screens.ResetPasswordScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = OrangePrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, OrangePrimary)
                ) {
                    Text("Reset Password", fontSize = 20.sp)
                }
            }
        }
    ) { innerPadding ->

        when (userProfileData) {
            is UiState.Error -> {}
            is UiState.Success -> {

                val userData = userProfileData.data.productData.user_data

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(OrangePrimary)
                        .padding(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding()),
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

                            Image(
                                painter = if (userData.profile_pic.isNullOrBlank())
                                    painterResource(id = R.drawable.man)
                                else
                                    rememberAsyncImagePainter(userData.profile_pic),
                                contentScale = ContentScale.Crop,
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .height(200.dp)
                                    .width(200.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .padding(16.dp)
                            )

                            // User Details
                            ProfileScreenUserDataComp(
                                icon = Icons.Default.AccountCircle,
                                text = "${userData.first_name} ${userData.last_name}"
                            )
                            ProfileScreenUserDataComp(
                                icon = Icons.Default.Email,
                                text = userData.email ?: ""
                            )
                            ProfileScreenUserDataComp(
                                icon = Icons.Default.Phone,
                                text = userData.phone_no ?: ""
                            )
                            ProfileScreenUserDataComp(
                                icon = if (userData.gender.equals("M")) Icons.Default.Male else Icons.Default.Female,
                                text = userData.gender ?: ""
                            )
                            ProfileScreenUserDataComp(
                                icon = Icons.Default.Cake,
                                text = userData.dob ?: ""
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    navController.navigate(Screens.EditProfileScreen.route)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = OrangeVariant,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(text = "Edit Profile")
                            }

                        }
                    }
                }

            }

            is UiState.Loading -> {
                LoaderComp()
            }

            else -> {}
        }

    }
}
