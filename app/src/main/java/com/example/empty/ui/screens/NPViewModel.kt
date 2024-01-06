package com.example.empty.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

class NPViewModel: ViewModel() {
    var moviesUiStateNP: MoviesUiState by mutableStateOf(MoviesUiState.Loading)

    init {
        getNowPlayingMovies()
    }

    private fun getNowPlayingMovies(){
        viewModelScope.launch {
            val nowPlayingDeferred = async(Dispatchers.IO) {
                Log.d("viewModelTest", "NP launched")
                try {
                    MoviesUiState.Success(MoviesApi.retrofitService.getNowPlaying())
                } catch (e: IOException) {
                    MoviesUiState.Error
                } finally {
                    Log.d("viewModelTest", "NP ended")
                }
            }
            moviesUiStateNP = nowPlayingDeferred.await()
        }
    }
}