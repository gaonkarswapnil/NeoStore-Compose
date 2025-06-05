package com.example.neostorecompose.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.neostorecompose.ui.components.BackgroundForScreens
import com.example.neostorecompose.ui.components.LoaderComp
import com.example.neostorecompose.ui.components.ProfileRow
import com.example.neostorecompose.ui.navigation.Screens
import com.example.neostorecompose.ui.viewmodel.DashboardViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState

@Composable
fun ProfileScreen(
    userViewModel: UserViewModel,
    dashboardViewModel: DashboardViewModel,
    navController: NavController
) {
    val accessToken = userViewModel.getAccessToken()
    LaunchedEffect(accessToken) {
        accessToken?.let { dashboardViewModel.getDashboard(it) }
    }
    val dashboardResponse = dashboardViewModel.dashboardRes.collectAsState().value

    Log.d("AccessTokenProfile", "ProfileScreenAccessTOken: ${accessToken} ")
    Log.d("AccessTokenProfile", "ProfileScreenResponse: ${dashboardResponse.toString()} ")

    Box(
        modifier = Modifier
            .BackgroundForScreens()
            .fillMaxSize()
    ) {

        when (dashboardResponse) {
            is UiState.Error -> {}
            is UiState.Loading -> {
                LoaderComp()
            }

            is UiState.Success -> {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    val res = dashboardResponse.data
//                    Log.d("AccessTokenProfileSuccess", "ProfileScreenResponse: ${ .toString()} ")
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(res.productData.user_data.profile_pic),
                                contentDescription = "",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .padding(3.dp)
                                    .height(100.dp)
                                    .width(100.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))


                            Column {
                                Text(
                                    text = "${res.productData.user_data.first_name} ${res.productData.user_data.last_name}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.Black
                                )
                                Text(
                                    text = res.productData.user_data.email,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.Gray
                                )
                            }
                        }
                    }


                    // Manage Account Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Manage Account",
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            ProfileRow(
                                image = Icons.Default.AccountCircle,
                                text = "Profile Information",
                                onClick = {
                                    navController.navigate(Screens.ProfileScreen.route)
                                }

                            )
                            ProfileRow(
                                image = Icons.Default.Translate, text = "Language Preferences",
                                onClick = {}

                            )
                            ProfileRow(
                                image = Icons.Default.Subscriptions,
                                text = "Manage Subscription",
                                onClick = {}
                            )
                        }
                    }

                    // Customer Support Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Customer Support",
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            ProfileRow(
                                image = Icons.Default.ContactSupport,
                                text = "Contact & Customer Support",
                                onClick = {}

                            )
                        }
                    }

                    // Account Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Account",
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            ProfileRow(
                                image = Icons.Default.List, text = "My Orders",
                                onClick = {
                                    navController.navigate(Screens.OrderListScreen.route)
                                }

                            )
                            ProfileRow(
                                image = Icons.Default.Logout, text = "Logout",
                                onClick = {}
                            )
                        }
                    }

                    // Communicate Card
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.LightGray)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Communicate",
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            ProfileRow(
                                image = Icons.Default.LocationCity, text = "Store Location",
                                onClick = {}
                            )
                            ProfileRow(
                                image = Icons.Default.StarRate, text = "Rate Us",
                                onClick = {}
                            )
                        }
                    }
                }

            }

            else -> {

            }
        }
    }

}
