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

class TRViewModel: ViewModel() {
    var moviesUiStateTR: MoviesUiState by mutableStateOf(MoviesUiState.Loading)

    init {
        getTRMovies()
    }



    private fun getTRMovies(){
        viewModelScope.launch {
            val trDeferred = async(Dispatchers.IO) {
                Log.d("viewModelTest", "TR launched")
                try {
                    MoviesUiState.Success(MoviesApi.retrofitService.getTopRated())
                } catch (e: IOException) {
                    MoviesUiState.Error
                } finally {
                    Log.d("viewModelTest", "TR ended")
                }
            }
            moviesUiStateTR = trDeferred.await()
        }
    }
}