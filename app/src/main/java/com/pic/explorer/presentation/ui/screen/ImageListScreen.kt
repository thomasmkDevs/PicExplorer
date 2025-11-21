package com.pic.explorer.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pic.explorer.R
import com.pic.explorer.presentation.ui.components.DropDownCommon
import com.pic.explorer.presentation.ui.components.SingleImageCardComponent
import com.pic.explorer.presentation.viewModels.ImageViewModel

@Composable
fun ImageListScreen(viewModel: ImageViewModel = hiltViewModel()) {
    val uiState by viewModel.imageUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.statusBars).windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painterResource(R.drawable.error),"error", modifier = Modifier.padding(bottom = 40.dp))
                        Text(
                            text = uiState.errorMessage ?: "Something went wrong",
                            color = Color.Red,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(
                            modifier = Modifier.padding(top = 20.dp),
                            onClick = { viewModel.getAllImages() }
                        ) {
                            Text(text = "Reload")
                        }
                    }
                }
            }
            else -> {
                DropDownCommon (
                    label = "All Authors",
                    items = uiState.authors,
                    selected = uiState.selectedAuthor,
                    itemToText = {it ?: "Unknown"},
                    onSelected = { viewModel.filterByAuthor(it) }
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    contentPadding = PaddingValues(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.filteredImages, key = { it.id!! }) { image ->
                        SingleImageCardComponent(image)
                    }
                }
            }
        }
    }
}