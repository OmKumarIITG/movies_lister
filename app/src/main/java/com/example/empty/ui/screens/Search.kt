package com.example.empty.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.empty.R
import com.example.empty.network.Result

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(
    navController: NavController
) {
    val searchViewModel : SearchViewModel = viewModel()
    Column(
        modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .background(Color.White)
        ){
            Text(
                "Search Your Movie",
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier=Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
            )
        }
        Row(
            modifier= Modifier
                .fillMaxWidth()
                .background(Color.White),
        ){
            IconButton(
                onClick = {
                    navController.navigate("home")
                }
            ) {
                Icon(
                    Icons.Default.ArrowBack,"back_to_home",
                    Modifier.size(25.dp)
                )
            }
            TextField(
                value = searchViewModel.getSearch(),
                onValueChange ={
                    searchViewModel.setSearch(it)
                },
                trailingIcon = {
                    Icon(
                        Icons.Default.Search,"search_here",
                        modifier= Modifier
                            .padding(top = 8.dp)
                            .size(35.dp)
                    )
                },
                placeholder = {
                    Text("Type your movie name here")
                },
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(end = 5.dp),
                shape = RoundedCornerShape(10.dp),
                colors=TextFieldDefaults.colors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
        }
        //search results will be shown here
        searchViewModel.searchDetails(searchViewModel.getSearch())
        val searchUiState = searchViewModel.searchUiState
        when(searchUiState){
            is SearchUiState.Success -> SearchScreen(searchUiState.searchResult.results,navController)
            is SearchUiState.Loading -> LoadingScreen()
            is SearchUiState.Error -> ErrorScreen()
        }
    }
}

@Composable
fun SearchScreen(
    movieList : List<Result>,
    navController: NavController
) {
    if(movieList.isEmpty()){
        Text(
            "No movies Found",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier=Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
    else{
        LazyColumn{
            items(movieList){movie->
                Row(
                    modifier= Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f)
                        .padding(5.dp)
                        .background(Color.LightGray)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(IMAGE_BASE_URL+movie.poster_path)
                            .crossfade(true)
                            .build(),
                        contentDescription = "movie_description",
                        contentScale = ContentScale.Inside, //to fill whole screen vertically and horizontally both
                        error = painterResource(id = R.drawable.ic_broken_image),//in case image failed to load
                        placeholder = painterResource(id = R.drawable.loading_img),//while its loaded , its shown
                        modifier= Modifier
                            .weight(0.4f)
                            .background(Color.Black),
                    )
                   Column(
                            modifier= Modifier
                                .fillMaxHeight()
                                .weight(0.6f),
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                movie.title,
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.ExtraBold,
                                color=Color.Blue
                            )
                            Text(
                                "Rating: ${movie.vote_average}/10",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                "Release Date: ${movie.release_date}",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            ClickableText(
                                modifier = Modifier.fillMaxWidth(),
                                text = AnnotatedString("Read more..."),
                                onClick ={
                                    navController.navigate("detail/${movie.id}")
                                },
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontStyle = FontStyle.Italic
                                )
                            )
                        }
                    }

            }
    }
    }
}
