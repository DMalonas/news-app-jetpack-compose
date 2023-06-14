package com.example.newsapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.newsapp.models.TopNewsArticle
import com.example.newsapp.network.NewsManager
import com.example.newsapp.ui.screen.Categories
import com.example.newsapp.ui.screen.DetailsScreen
import com.example.newsapp.ui.screen.Sources
import com.example.newsapp.ui.screen.TopNews

@Composable
fun NewsApp() {
    MainScreen(rememberNavController(), rememberScrollState())
}



//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreen(navController: NavHostController, scrollState: ScrollState,
//               newsManager: NewsManager = NewsManager()) {
//    Scaffold(
//        bottomBar = {
//            BottomMenu(navController)
//        }
//    ) { innerPadding -> // padding calculated by scaffold
//        // it doesn't have to be a column,
//        // can be another component that accepts a modifier with padding
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues = innerPadding) // padding applied here
//        ) {
//            // all content should be here
//            Navigation(navController, scrollState, newsManager, paddingValues = innerPadding)
//        }
//    }
//}


@Composable
fun MainScreen(navController: NavHostController,scrollState: ScrollState) {
    androidx.compose.material.Scaffold(bottomBar = {
        BottomMenu(navController = navController)
    }) {
        Navigation(navController = navController, scrollState = scrollState,
            newsManager = NewsManager(), paddingValues = it)
    }
}

//@Composable
//fun Navigation(navController: NavHostController,
//               scrollState: ScrollState,
//               newsManager: NewsManager,
//               paddingValues: PaddingValues) {
//    val articles = newsManager.newsResponse.value.articles
//    Log.d("news", "$articles")
//
//    NavHost(navController =  navController, startDestination =
//        BottomMenuScreen.TopNews.route, modifier = Modifier.padding(paddingValues = paddingValues)) {
//        articles?.let {
//            bottomNavigation(navController, articles)
////            composable("TopNews") {
////                TopNews(navController)
//////            navController.popBackStack()
////            }
//            composable("Details/{index}",
//                arguments = listOf(navArgument("index") { type = NavType.IntType })
//            ) {
//                navBacstackEntry -> val index = navBacstackEntry.arguments?.getInt("index")
//                index?.let {
//                    val article = articles[index]
//                    DetailsScreen(article, scrollState, navController)
//                }
//
//            }
//        }
//        }
//
//}

@Composable
fun Navigation(navController:NavHostController,scrollState: ScrollState,newsManager: NewsManager= NewsManager(),paddingValues: PaddingValues) {

    val articles = newsManager.newsResponse.value.articles
    Log.d("newss","$articles")
    articles?.let {
        NavHost(navController = navController, startDestination =BottomMenuScreen.TopNews.route,modifier = Modifier.padding(paddingValues)) {
            //Todo 7:pass articles to bottomNavigation
            bottomNavigation(navController = navController, articles)
            //Todo 12: replace the key with index and get article by selected index
            composable("Detail/{index}",
                arguments = listOf(
                    navArgument("index") { type = NavType.IntType }
                )) { navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    val article = articles[index]
                    DetailsScreen(article, scrollState, navController)
                }
            }
        }
    }
}

fun NavGraphBuilder.bottomNavigation(navController: NavHostController, articles:List<TopNewsArticle>) {
    composable(BottomMenuScreen.TopNews.route) {
        TopNews(navController, articles)
    }
    composable(BottomMenuScreen.Categories.route) {
        Categories()
    }
    composable(BottomMenuScreen.Sources.route) {
        Sources()
    }
}