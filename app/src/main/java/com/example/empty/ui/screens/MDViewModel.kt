package com.example.empty.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.empty.network.MovieDetails
import com.example.empty.network.MoviesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface DetailUiState {
    data class Success(val detail : MovieDetails) : DetailUiState
    object Error : DetailUiState
    object Loading : DetailUiState
}

//its movie detail viewModel
class MDViewModel() :ViewModel(){
    var detailUiState: DetailUiState by mutableStateOf(DetailUiState.Loading)
    fun getDetails(id:Int){
        viewModelScope.launch {
            val detailDeferred = async(Dispatchers.IO) {
                try {
                    DetailUiState.Success(MoviesApi.retrofitService.getMovieDetails(id))
                } catch (e: IOException) {
                    DetailUiState.Error
                }
            }
            detailUiState= detailDeferred.await()
        }
    }
}