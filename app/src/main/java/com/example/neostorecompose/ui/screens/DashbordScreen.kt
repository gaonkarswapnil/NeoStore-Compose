package com.example.neostorecompose.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.neostorecompose.ui.components.AutoSlidingImageSliderComp
import com.example.neostorecompose.ui.components.BackgroundForScreens
import com.example.neostorecompose.ui.components.SearchBarComp
import com.example.neostorecompose.ui.components.StaggeredRecyclerView
import com.example.neostorecompose.ui.theme.OrangePrimary
import com.example.neostorecompose.ui.viewmodel.UserViewModel

@Composable
fun DashboardScreen(navController:NavController) {

    var searchQuery by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .BackgroundForScreens()
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            SearchBarComp(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            AutoSlidingImageSliderComp(imageList = images)

            StaggeredRecyclerView(
                categoryList = ,
                navController = navController
                )

        }
    }
}


//
//1 SearchBar//
//2 ViewPager
//3 LazyVerticalGrid


