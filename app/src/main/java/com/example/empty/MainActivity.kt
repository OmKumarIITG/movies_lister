package com.example.empty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.empty.ui.screens.Nav

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Nav()
//            var search by remember {
//                mutableStateOf("")
//            }
//            Column(
//                modifier= Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//            ){
//                Row(
//                    modifier= Modifier
//                        .fillMaxWidth()
//                        .background(Color.White)
//                ){
//                    Text(
//                        "Search Your Movie",
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                        modifier= Modifier.fillMaxWidth(),
//                        fontWeight = FontWeight.Bold,
//                    )
//                }
//                Row(
//                    modifier= Modifier
//                        .fillMaxWidth()
//                        .background(Color.White),
////            horizontalArrangement = Arrangement.SpaceAround
//                ){
//                    IconButton(
//                        onClick = {
////                    navController.navigate("home")
//                        }
//                    ) {
//                        Icon(
//                            Icons.Default.ArrowBack,"back_to_home",
//                            Modifier.size(25.dp)
//                        )
//                    }
//                    TextField(
//                        value = search,
//                        onValueChange ={
//                            search = it
//                        },
//                        trailingIcon = {
//                            Icon(
//                                Icons.Default.Search,"search_here",
//                                modifier= Modifier
//                                    .padding(top = 8.dp)
//                                    .size(35.dp)
//                            )
//                        },
//                        placeholder = {
//                            Text("Type your movie name here")
//                        },
//                        modifier= Modifier
//                            .fillMaxWidth()
//                            .padding(end = 5.dp),
//                        shape = RoundedCornerShape(10.dp),
//                        colors= TextFieldDefaults.colors(
//                            disabledTextColor = Color.Transparent,
//                            focusedIndicatorColor = Color.Transparent,
//                            unfocusedIndicatorColor = Color.Transparent,
//                            disabledIndicatorColor = Color.Transparent
//                        )
//                    )
//                }
        }
    }
}

