package com.example.empty.ui.screens

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
import okio.IOException

sealed interface SearchUiState {
    data class Success(val searchResult : MovieResult) : SearchUiState
    object Error : SearchUiState
    object Loading : SearchUiState
}

class SearchViewModel:ViewModel() {
    var searchUiState:SearchUiState by mutableStateOf(SearchUiState.Loading)
    private var searchValue:String by mutableStateOf("")

    fun getSearch():String{
        return searchValue
    }
    fun setSearch(set:String){
        searchValue = set
    }

    fun searchDetails(
        name:String
    ){
        viewModelScope.launch {
            val searchDeferred = async(Dispatchers.IO) {
                try {
                    SearchUiState.Success(MoviesApi.retrofitService.searchDetails(name))
                } catch (e: IOException) {
                    SearchUiState.Error
                }
            }
            searchUiState= searchDeferred.await()
        }
    }
}