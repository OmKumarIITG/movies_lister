package com.example.empty.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavController
import com.example.empty.datastore.PreferenceDataStoreHelper
import kotlinx.coroutines.runBlocking

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchList(
    navController: NavController
) {
    val datastore=PreferenceDataStoreHelper(LocalContext.current)
//    runBlocking {
//        val allKeys = datastore.getAllKeys()
////        Log.d("watchList",allKeys)
//        val allValues = datastore.getAllValues()
//        Log.d("watchList",allValues.toString())
//    }
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("WatchList")
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("home") }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,"back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ){
        var refresh by remember {
            mutableStateOf(false)
        }
        Log.d("recompose","recompose happend and refresh: $refresh")
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(it)
        ){
            val allKeys : Set<Preferences.Key<*>>
            runBlocking {
                allKeys = datastore.getAllKeys()
            }
            if(allKeys.isEmpty()){
                Box(
                    modifier=Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        "WatchList is Empty",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            else{
                allKeys.forEachIndexed { index, key ->
                    Row(
                        modifier= Modifier
                            .fillMaxWidth()
                            .background(Color.Green)
                    ){
                        Text("${index+1}",
                            Modifier
                                .padding(start = 5.dp, top = 3.dp)
                                .weight(0.2f))
                        Text(
                            runBlocking {
                                datastore.getFirstPreference(stringPreferencesKey(key.toString()),"")
                            },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            fontFamily = FontFamily.Monospace,
                            modifier= Modifier
                                .clickable {
                                    navController.navigate("detail/${key}")
                                }
                                .weight(0.5f)
                        )
                        IconButton(
                            onClick = {
                                runBlocking {
                                    datastore.removePreference(stringPreferencesKey(key.toString()))
                                    refresh=!refresh
                                    Log.d("recompose","deleted pressed, refresh: $refresh")
                                }
                            },
                            modifier= Modifier
                                .height(30.dp)
                                .weight(0.3f)
                        ) {
                            Icon(
                                Icons.Default.Delete,"remove_from_watchList"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Item(
    navController: NavController,
    title:String,
    id:String,
    index:Int,
    database:PreferenceDataStoreHelper,
    refresh:Boolean
) {
    Row(
        modifier= Modifier
            .fillMaxWidth()
            .background(Color.Green)
    ){
        Text("$index.",
            Modifier
                .padding(start = 5.dp, top = 3.dp)
                .weight(0.2f))
        Text(
            title,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Monospace,
            modifier= Modifier
                .clickable {
                    navController.navigate("detail/${id}")
                }
                .weight(0.5f)
        )
        IconButton(
            onClick = {
                runBlocking {
                    database.removePreference(stringPreferencesKey(id))
                }
            },
            modifier= Modifier
                .height(30.dp)
                .weight(0.3f)
        ) {
            Icon(
                Icons.Default.Delete,"remove_from_watchList"
            )
        }

    }
}
