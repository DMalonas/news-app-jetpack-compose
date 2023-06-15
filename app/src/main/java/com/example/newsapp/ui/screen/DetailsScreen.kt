package com.example.newsapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.MockData
import com.example.newsapp.MockData.getTimeAgo
import com.example.newsapp.NewsData
import com.example.newsapp.R
import com.example.newsapp.models.TopNewsArticle
import com.skydoves.landscapist.coil.CoilImage

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(article: TopNewsArticle, scrollState: ScrollState, navController: NavController) {

    Scaffold(topBar = {
        DetailTopAppBar(onBackPressed = {navController.popBackStack()})
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Details Screen", fontWeight = FontWeight.SemiBold)

            CoilImage(
                imageModel = article.urlToImage,
                contentScale = ContentScale.Crop,
                error = ImageBitmap.imageResource(R.drawable.breaking_news)
            )

//        Button(
//            onClick = {
//                navController.navigate("TopNews") {
//                    popUpTo("Details") {
//                        inclusive = true
//                    }
//                }
//            }
//        ) {
//            Text(text = "Go to Top News Screen + ${newsData.author}")
//        }
//            Image(
//                painterResource(
//                    id = article.im),
//                contentDescription = "")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                InfoWithIcon(Icons.Default.Edit, info = article.author?:"Not available")
                InfoWithIcon(Icons.Default.DateRange, info = MockData.stringToDate(article.publishedAt!!).getTimeAgo())
            }
            Text(text=article.title?:"Not Available", fontWeight = FontWeight.Bold)
            Text(text=article.description?:"Not Available", modifier = Modifier.padding(top=16.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopAppBar(onBackPressed: ()-> Unit = {}) {
    TopAppBar(
        title = {
            Text(
                text="Detail Screen",
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBackPressed() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "")
            }
        }
    )
}

@Composable
fun InfoWithIcon(icon: ImageVector, info: String) {
    Row {
        Icon(icon, contentDescription = "Author",
            modifier = Modifier.padding(end=8.dp),
            colorResource(id = R.color.purple_500)
        )
        Text(text=info)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
//    rememberNavController(),
    DetailsScreen(
        TopNewsArticle(
            author = "Namita Singh",
            title = "Cleo Smith news — live: Kidnap suspect 'in hospital again' as 'hard police grind' credited for breakthrough - The Independent",
            description = "The suspected kidnapper of four-year-old Cleo Smith has been treated in hospital for a second time amid reports he was “attacked” while in custody.",
            publishedAt = "2021-11-04T04:42:40Z"
        ),
        rememberScrollState(),
        rememberNavController()
        )
}