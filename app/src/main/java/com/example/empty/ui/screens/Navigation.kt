package com.example.empty.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Nav() {
    val navController = rememberNavController()
    val pViewModel : PViewModel = viewModel()
    val trViewModel : TRViewModel = viewModel()
    val npViewModel : NPViewModel = viewModel()
    NavHost(navController = navController, startDestination = "start"){
        composable("start"){
            Start(navController)
        }
        composable("home"){
            Home(
                navController,
                pViewModel.moviesUiStateP,
                trViewModel.moviesUiStateTR,
                npViewModel.moviesUiStateNP
            )
        }
        composable(
            "detail/{id}",
            arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                }
            )
        ){
            val id = it.arguments?.getInt("id") ?: 0
            MovieDetail(navController = navController, movieId = id)
        }
        composable("watchList"){
            WatchList(navController)
        }
        composable("search"){
            Search(navController)
        }
    }
}