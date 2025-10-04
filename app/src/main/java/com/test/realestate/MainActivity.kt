package com.test.realestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.test.realestate.listpage.PropertiesScreen
import com.test.realestate.detailspage.PropertyDetailScreen
import com.test.realestate.ui.theme.AvivTaskTheme
import com.test.realestate.viewmodel.PropertyDetailViewModel.Companion.LISTING_ID_KEY
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AvivTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.PropertiesScreenRoute.route
                    ) {
                        composable(route = ScreenRoute.PropertiesScreenRoute.route) {
                            PropertiesScreen(navController = navController)
                        }

                        composable(
                            route = ScreenRoute.PropertyDetailScreenRoute.route,
                            arguments = listOf(navArgument(LISTING_ID_KEY) { type = NavType.IntType })
                        ) {
                            PropertyDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}