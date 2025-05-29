package com.example.neostorecompose.core

import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.neostorecompose.ui.navigation.SetUpNav

@Composable
fun NeoStore(){
    val navController = rememberNavController()
    SetUpNav(navController)
}