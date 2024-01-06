package com.example.empty.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.empty.R

@Composable
fun Start(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Box(
            modifier=Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.drawable.start),
                contentDescription = "start",
                modifier = Modifier.size(1000.dp)
            )
        }
        Box(
            modifier=Modifier.fillMaxSize().padding(bottom = 200.dp),
            contentAlignment = Alignment.BottomCenter
        ){
            Button(
                onClick = {
                navController.navigate("home")
                }
            ) {
                Text("Enter")
            }
        }
    }
}
