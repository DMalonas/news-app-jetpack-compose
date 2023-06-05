package com.example.newsapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.MockData
import com.example.newsapp.ui.screen.DetailsScreen
import com.example.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    Navigation()
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val scrollState = rememberScrollState()
    NavHost(navController =  navController, startDestination = "TopNews") {
        composable("TopNews") {
            TopNews(navController)
//            navController.popBackStack()
        }
        composable("Details/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) {
            navBacstackEntry -> val id = navBacstackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailsScreen(newsData, scrollState)
        }
    }
}