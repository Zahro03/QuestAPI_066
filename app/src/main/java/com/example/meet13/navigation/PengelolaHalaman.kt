package com.example.meet13.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.meet13.ui.view.DestinasiDetail
import com.example.meet13.ui.view.DestinasiEntry
import com.example.meet13.ui.view.DestinasiHome
import com.example.meet13.ui.view.DestinasiUpdate
import com.example.meet13.ui.view.DetailMhsScreen
import com.example.meet13.ui.view.EntryMhsScreen
import com.example.meet13.ui.view.HomeScreen
import com.example.meet13.ui.view.UpdateMhsScreen

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntry.route) },
                onDetailClick = { nim ->
                    // Navigasi ke halaman detail mahasiswa
                    navController.navigate("${DestinasiDetail.route}/$nim")
                }
            )
        }
        composable(DestinasiEntry.route) {
            EntryMhsScreen(navigateBack = {
                navController.navigate(DestinasiHome.route) {
                    popUpTo(DestinasiHome.route) {
                        inclusive = true
                    }
                }
            })
        }

        composable(
            DestinasiDetail.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM) {
                    type = NavType.StringType
                }
            )
        ) {
            val nim = it.arguments?.getString(DestinasiDetail.NIM)
            nim?.let { nim ->
                DetailMhsScreen(
                    navigateBack = { navController.navigateUp() },
                    onEditClick = { nim ->
                        navController.navigate("${DestinasiUpdate.route}/$nim")
                        println(nim)
                    }
                )
            }
        }
        composable(
            DestinasiUpdate.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiDetail.NIM) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMhsScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = {
                    navController.navigate(DestinasiHome.route)
                    {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}