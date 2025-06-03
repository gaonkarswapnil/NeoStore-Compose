package com.example.neostorecompose.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.neostorecompose.ui.navigation.SealedBottomNavItem

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {
    val items = listOf(
        SealedBottomNavItem.dashboard,
        SealedBottomNavItem.search,
        SealedBottomNavItem.cart,
        SealedBottomNavItem.userprofile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 4.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector =  item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp),
                    )
                },
                label = { Text(text = item.label) },
                selected = currentDestination?.route == item.route,
                onClick = {
                    if (currentDestination?.route != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
//                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
