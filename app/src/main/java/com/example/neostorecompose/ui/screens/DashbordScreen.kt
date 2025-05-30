package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.neostorecompose.ui.components.*
import com.example.neostorecompose.ui.viewmodel.DashboardViewModel
import com.example.neostorecompose.ui.viewmodel.UserViewModel
import com.example.neostorecompose.utils.UiState
import okhttp3.internal.format


@Composable
fun DashboardScreen(navController: NavController) {
    val userViewModel: UserViewModel = hiltViewModel()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()

    val dashboardUiState = dashboardViewModel.dashboardRes.collectAsState().value
    val accessToken = userViewModel.getAccessToken()
    val imageList = remember { mutableStateListOf<String>() }

    LaunchedEffect(accessToken) {
        accessToken?.let { dashboardViewModel.getDashboard(it) }
    }

    var searchQuery by remember { mutableStateOf("") }

    Box(modifier = Modifier.BackgroundForScreens()) {
        when (dashboardUiState) {
            is UiState.Loading -> {
                LoaderComp()
            }

            is UiState.Success -> {
                val profileData = dashboardUiState.data
                val productCategoryList = profileData.data.product_categories

                for (image in productCategoryList) {
                    if (image.icon_image.isNotEmpty()) {
                        imageList.add(image.icon_image)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(3.dp),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(6.dp))

                        SearchBarComp(
                            query = searchQuery,
                            onQueryChange = { searchQuery = it }
                        )
                    }

                    item {
                        AutoSlidingImageSliderComp(imageUrls = imageList)
                    }

                    item {
                        // Constrain height to prevent infinite measure
                        Box(modifier = Modifier.height(400.dp)) {
                            StaggeredRecyclerView(
                                categoryList = productCategoryList,
                                navController = navController
                            )
                        }
                    }

//                    item {
//                        Spacer(modifier = Modifier.height(24.dp))
//                    }
                }
            }

            is UiState.Error -> {
                // Show error if needed
            }

            else -> {}
        }
    }
}
