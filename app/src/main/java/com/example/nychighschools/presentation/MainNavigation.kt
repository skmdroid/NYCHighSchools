package com.example.nychighschools.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nychighschools.utils.Screens

@Composable
fun MainNavigation(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(navController = navHostController, startDestination = Screens.HomeScreen.name) {
        composable(Screens.HomeScreen.name) { HomeScreen(navController = navHostController) }
        composable(
            Screens.DetailScreen.name + "/{schoolName}/{overview}",
            arguments = listOf(navArgument("schoolName") {
                type = NavType.StringType
            }, navArgument("overview") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val schoolName = navBackStackEntry.arguments?.getString("schoolName") ?: ""
            val overview = navBackStackEntry.arguments?.getString("overview") ?: ""

            DetailsScreen(
                navController = navHostController,
                schoolName = schoolName,
                overview = overview,
            )

        }
    }
}