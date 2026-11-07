package com.odogwudev.liveinterview.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppRoot(viewModel: CharactersViewModel) {
    val navController = rememberNavController()

    NavHost (navController, startDestination = "characters") {
        composable("characters") {
            CharactersScreen(viewModel = viewModel){
                navController.navigate("favorites")
            }
        }
        composable("favorites") {
            FavoritesScreen(viewModel = viewModel, onGoBack = {navController.popBackStack()})
        }

    }
}