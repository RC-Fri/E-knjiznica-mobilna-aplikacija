package com.example.e_knjiznica_mobilna_aplikacija

import LoginViewModel
import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.e_knjiznica_mobilna_aplikacija.data.model.MainViewModel
import com.example.e_knjiznica_mobilna_aplikacija.data.model.SearchViewModel
import com.example.e_knjiznica_mobilna_aplikacija.ui.login.LoginScreen
import com.example.e_knjiznica_mobilna_aplikacija.ui.main.MainScreen
import com.example.e_knjiznica_mobilna_aplikacija.ui.search.SearchScreen

object Routes {
    const val LOGIN = "login"
    const val MAIN = "main/{userId}/{username}"
    const val SEARCH = "search/{userId}/{username}"
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            val loginViewModel: LoginViewModel = viewModel()
            val uiState by loginViewModel.uiState.collectAsState()

            LoginScreen(
                uiState = uiState,
                onLogin = { username, password ->
                    loginViewModel.login(username, password)
                },
                onLoginSuccess = { userId ->
                    navController.navigate("main/$userId/${uiState.username}")
                },
            )
        }
        composable(
            route = Routes.MAIN,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType },
                navArgument("username") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val username = backStackEntry.arguments?.getString("username") ?: "User"
            val mainViewModel: MainViewModel = viewModel()
            mainViewModel.loadMaterialsForUser(userId)

            MainScreen(
                userId = userId,
                userName = username,
                mainViewModel = mainViewModel,
                onBorrowMaterial = {
                    navController.navigate("search/$userId/$username")
                },
                materials = mainViewModel.materials.value,
                onExtendDate = { material -> mainViewModel.extendMaterialDate(material, userId) },
                onView = { material -> mainViewModel.viewMaterialDetails(material) }
            )
        }
        composable(
            route = Routes.SEARCH,
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
            val username = backStackEntry.arguments?.getString("username") ?: "User"
            val searchViewModel: SearchViewModel = viewModel()
            searchViewModel.loadMaterials()

            SearchScreen(
                userId = userId, searchViewModel = searchViewModel,
                onBorrowMaterial = { material ->
                    searchViewModel.borrowMaterial(material.id, userId)
                },
                onExit = {
                    navController.navigate("main/$userId/$username")
                }
            )
        }
    }
}
