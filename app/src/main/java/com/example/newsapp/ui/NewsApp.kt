package com.example.newsapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Scaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.BottomMenuScreen
import com.example.newsapp.MockData
import com.example.newsapp.components.BottomMenu
import com.example.newsapp.ui.screen.Categories
import com.example.newsapp.ui.screen.DetailsScreen
import com.example.newsapp.ui.screen.Sources
import com.example.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    MainScreen(rememberNavController(), rememberScrollState())
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState) {
    Scaffold(
        bottomBar = {
            BottomMenu(navController)
        }
    ) { innerPadding -> // padding calculated by scaffold
        // it doesn't have to be a column,
        // can be another component that accepts a modifier with padding
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding) // padding applied here
        ) {
            // all content should be here
            Navigation(navController, scrollState)
        }
    }
}
@Composable
fun Navigation(navController: NavHostController, scrollState: ScrollState) {
    NavHost(navController =  navController, startDestination = "TopNews") {
        bottomNavigation(navController)
        composable("TopNews") {
            TopNews(navController)
//            navController.popBackStack()
        }
        composable("Details/{newsId}",
            arguments = listOf(navArgument("newsId") { type = NavType.IntType })
        ) {
            navBacstackEntry -> val id = navBacstackEntry.arguments?.getInt("newsId")
            val newsData = MockData.getNews(id)
            DetailsScreen(newsData, scrollState, navController)
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavHostController) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}