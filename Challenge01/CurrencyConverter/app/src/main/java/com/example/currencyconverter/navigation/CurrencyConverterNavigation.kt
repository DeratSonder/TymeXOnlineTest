package com.example.currencyconverter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyconverter.screens.ChooseCurrencyScreen
import com.example.currencyconverter.screens.HomeScreen
import com.example.currencyconverter.screens.NoInternetScreen
import com.example.currencyconverter.viewmodels.CurrencyConverterViewModel

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object ChooseCurrency : Screen("choose_currency/{code}"){
        fun createRoute(code: Int) = "choose_currency/$code"
    }
    data object NoInternet : Screen("no_internet")
}

@Composable
fun CurrencyConverterNavigation(
    modifier: Modifier = Modifier,
    netWorkStatus: String,
    viewModel: CurrencyConverterViewModel = hiltViewModel(),
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(
            route = Screen.Home.route,
        ) {
            HomeScreen(
                onChooseCurrency = {
                    navController.navigate(Screen.ChooseCurrency.createRoute(it))
                },
                viewModel = viewModel
            )
        }

        composable(Screen.ChooseCurrency.route) {

            val code = it.arguments?.getString("code")?.toInt() ?: 0

            if(code != 0){
                ChooseCurrencyScreen(
                    onChoose = { navController.navigateUp() },
                    status = code,
                    viewModel = viewModel
                )
            }
        }

        composable(Screen.NoInternet.route) {
            NoInternetScreen(
                onRefresh = {
                    if (netWorkStatus == "Available"){
                        navController.navigateUp()
                    }
                }
            )

        }
    }

    LaunchedEffect(netWorkStatus){
        if (netWorkStatus != "Available"){
            navController.navigate(Screen.NoInternet.route)
        } else {
            navController.navigateUp()
        }
    }

}