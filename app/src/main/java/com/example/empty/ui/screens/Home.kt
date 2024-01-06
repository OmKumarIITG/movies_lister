package com.example.empty.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.empty.R
import com.example.empty.network.MovieResult
import com.example.empty.network.Result

const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    moviesUiStateP: MoviesUiState,
    moviesUiStateTR: MoviesUiState,
    moviesUiStateNP: MoviesUiState
) {
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Movies Universe",modifier=Modifier.padding(vertical = 2.dp))
                },
                modifier=Modifier.height(40.dp)
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
                modifier=Modifier.height(50.dp)
            ) {
                Row(
                    modifier=Modifier.fillMaxWidth(),
                ){
                    IconButton(
                        onClick = {
                                  navController.navigate("search")
                        },
                        Modifier.weight(0.5f)
                    ) {
                        Column{
                            Icon(Icons.Default.Search,contentDescription = "search")
                            Text("Search")
                        }
                    }
                    IconButton(
                        onClick = {
                                  navController.navigate("watchList")
                        },
                        Modifier.weight(0.5f)
                    ) {
                        Column {
                            Icon(Icons.Default.List,contentDescription = "watchList")
                            Text("WatchList")
                        }
                    }
                }
            }
        }
    ){
        LazyColumn(
            modifier= Modifier
                .fillMaxSize()
                .padding(it),
            contentPadding = PaddingValues(4.dp)
        ){
            item{
                Text(
                    "Popular Movies",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier=Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            item{
                when (moviesUiStateP) {
                    is MoviesUiState.Success -> {
                        SuccessScreen(moviesUiStateP.movieResult,400.dp,navController)
                    }
                    is MoviesUiState.Error -> ErrorScreen()
                    is MoviesUiState.Loading -> LoadingScreen()
                }
            }
            item{
                Text(
                    "Top-Rated Movies",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier=Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            item{
                when(moviesUiStateTR){
                    is MoviesUiState.Success -> {
                        SuccessScreen(moviesUiStateTR.movieResult,200.dp,navController)
                    }
                    is MoviesUiState.Error ->ErrorScreen()
                    is MoviesUiState.Loading ->LoadingScreen()
                }
            }
            item{
                Text(
                    "Now-Playing Movies",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier=Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            item{
                when(moviesUiStateNP){
                    is MoviesUiState.Success -> {
                        SuccessScreen(moviesUiStateNP.movieResult,200.dp,navController)
                    }
                    is MoviesUiState.Error ->ErrorScreen()
                    is MoviesUiState.Loading ->LoadingScreen()
                }
            }
    }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesPhotoCard(
    movie:Result,
    modifier : Modifier = Modifier,
    navController: NavController
) {
    Card(
        //wrap Async img to card
        modifier=modifier,
        elevation= CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = {
            navController.navigate("detail/${movie.id}")
        }
    ) {
        Box(
            modifier=Modifier.fillMaxSize()
        ){
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(IMAGE_BASE_URL+movie.poster_path)
                    .crossfade(true)
                    .build(),
                contentDescription = "movie_description",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop, //to fill whole screen vertically and horizontally both
                error = painterResource(id = R.drawable.ic_broken_image),//in case image failed to load
                placeholder = painterResource(id = R.drawable.loading_img)//while its loaded , its shown
            )
            Box(
                modifier= Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black,
                            ),
                            startY = 300f
                        )
                    )
            )
            Box(
                modifier= modifier
                    .fillMaxSize()
                    .padding(12.dp),//it sets size wrt its parent container
                contentAlignment = Alignment.BottomStart
            ) {
                //for font size use sp , else everywhere dp
                Text(movie.title,style= TextStyle(color= Color.White, fontSize = 16.sp))
            }
        }
    }
}

@Composable
fun SuccessScreen(
    movieResult : MovieResult,
    height:Dp,
    navController: NavController
) {
    val results = movieResult.results
    LazyHorizontalGrid(
        rows = GridCells.Fixed(1),
        modifier=Modifier.height(height),
        contentPadding = PaddingValues(4.dp)
    ){
        items(
            items=results,
            key={result->
                result.id
            }
        ){result->
            MoviesPhotoCard(
                movie = result,
                modifier= Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                navController
            )
        }
    }
}


@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = "loading failed", modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun LoadingScreen() {
    Image(
        modifier = Modifier
            .size(200.dp)
            .fillMaxSize(),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = "loading"
    )
}