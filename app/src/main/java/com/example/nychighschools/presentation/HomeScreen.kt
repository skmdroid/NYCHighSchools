package com.example.nychighschools.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nychighschools.utils.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val data by viewModel.data.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.error.collect { tes ->
            Log.d("Test1234", tes ?: "")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "NYC High Schools")
                }
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = 16.dp,
                        bottom = 16.dp,
                        start = 12.dp,
                        end = 12.dp
                    )
                ) {
                    items(count = data.size, itemContent = {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable {
                                    navController.navigate(
                                        Screens.DetailScreen.name
                                                + "/" + data[it].schoolName
                                                + "/" + data[it].overviewParagraph
                                    )
                                }
                        ) {
                            Text(
                                text = "" + data[it].schoolName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(text = "Dbn: ${data[it].dbn}")
                                Text(text = "City: ${data[it].city}")
                            }
                            Text(text = "Total Students: " + data[it].totalStudents)

                            Text(text = "School Email: ${data[it].schoolEmail}")


                            Divider(
                                color = Color.Gray,
                                thickness = 1.dp,
                                modifier = Modifier
                                    .width(100.dp)
                                    .align(alignment = Alignment.CenterHorizontally)
                                    .padding(top = 20.dp)
                            )
                        }
                    })
                }

                if (isLoading) {
                    androidx.compose.material.CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
        }
    )
}