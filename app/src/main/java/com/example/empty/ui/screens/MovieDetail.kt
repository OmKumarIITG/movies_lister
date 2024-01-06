package com.example.empty.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.empty.R
import com.example.empty.datastore.PreferenceDataStoreHelper
import com.example.empty.network.MovieDetails
import kotlinx.coroutines.runBlocking

@Composable
fun MovieDetail(
    navController: NavController,
    movieId:Int
) {
    Log.d("idddddzdsdz","movie id isss ${movieId}")
    val detailViewModel: MDViewModel = viewModel()
    detailViewModel.getDetails(movieId)
    val detailUiState = detailViewModel.detailUiState
    when (detailUiState) {
        is DetailUiState.Success -> {
            Log.d("success","success")
            DetailSuccessScreen(detailUiState.detail,navController)
        }
        is DetailUiState.Error -> {
            Log.d("errprrr","error")
            ErrorScreen()
        }
        is DetailUiState.Loading -> LoadingScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSuccessScreen(
    detail:MovieDetails,
    navController: NavController
) {
    val datastore = PreferenceDataStoreHelper(LocalContext.current)
    val context = LocalContext.current
    val font = FontFamily(
        Font(R.font.merriweather_italic)
    )
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(detail.title, textAlign = TextAlign.Center)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Localized description"
                        )
                    }
                },
            )
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
           AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(IMAGE_BASE_URL+detail.poster_path)
                        .crossfade(true)
                        .build(),
                    contentDescription = "movie_description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f),
                    contentScale = ContentScale.Inside, //to fill whole screen vertically and horizontally both
                    error = painterResource(id = R.drawable.ic_broken_image),//in case image failed to load
                    placeholder = painterResource(id = R.drawable.loading_img)//while its loaded , its shown
                )
            Text(
                detail.overview,
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                modifier=Modifier.padding(5.dp),
                fontFamily = font
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Vote Average
                Column(
                    modifier=Modifier.fillMaxSize()
                ){
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color= Color.Blue,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                append("Vote Average : ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                append("${detail.vote_average}/10")
                            }
                        }
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color= Color.Blue,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                append("Release Date : ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                append("${detail.release_date}")
                            }
                        }
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color= Color.Blue,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                append("Genre : ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ){
                                detail.genres.forEach {
                                    append(it.name+" ")
                                }
                            }
                        }
                    )
                    Row(
                        modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color= Color.Blue,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ){
                                    append("RunTime : ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                ){
                                    append("${detail.runtime} min")
                                }
                            }
                        )
                        FloatingActionButton(
                            onClick = {
                                runBlocking {
                                    val id = stringPreferencesKey(detail.id.toString())
                                    val title = detail.title
                                    datastore.putPreference(id,title)
                                }
                                Toast.makeText(context,"Added to WatchList",Toast.LENGTH_SHORT).show()
                            },
                            modifier=Modifier.padding(top=5.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }

                }

            }
        }
    }
}