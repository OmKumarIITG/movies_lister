package com.example.empty.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "ead8791c11fc19aeed01db3477b67d1f"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface MoviesApiService{
    @GET("movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies() : MovieResult

    @GET("movie/top_rated?api_key=$API_KEY")
    suspend fun getTopRated(): MovieResult

    @GET("movie/now_playing?api_key=$API_KEY")
    suspend fun getNowPlaying(): MovieResult

    @GET("movie/{id}?api_key=$API_KEY")
    suspend fun getMovieDetails(
        @Path("id") id: Int
    ) : MovieDetails

    @GET("search/movie")
    suspend fun searchDetails(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): MovieResult
}

object MoviesApi {
    val retrofitService : MoviesApiService by lazy {
        retrofit.create(MoviesApiService::class.java)
    }
}