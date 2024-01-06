package com.example.empty.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty.network.MovieResult
import com.example.empty.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MoviesUiState {
    data class Success(val movieResult : MovieResult) : MoviesUiState
    object Error :  MoviesUiState
    object Loading :  MoviesUiState
}

class PViewModel:ViewModel() {
    var moviesUiStateP: MoviesUiState by mutableStateOf(MoviesUiState.Loading)

    init {
        getPopularMovies()
    }

    private fun getPopularMovies(){
        viewModelScope.launch {
            val popularDeferred = async(Dispatchers.IO) {
                Log.d("viewModelTest", "Popular movies launched in ${Thread.currentThread().name}")
                try {
                    MoviesUiState.Success(MoviesApi.retrofitService.getPopularMovies())
                } catch (e: IOException) {
                    MoviesUiState.Error
                } finally {
                    Log.d("viewModelTest", "Popular movies ended")
                }
            }
            moviesUiStateP = popularDeferred.await()
        }
    }
}