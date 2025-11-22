package com.pic.explorer.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pic.explorer.R
import com.pic.explorer.presentation.ui.components.DropDownCommon
import com.pic.explorer.presentation.ui.components.SingleImageCardComponent
import com.pic.explorer.presentation.uiModels.SortType
import com.pic.explorer.presentation.viewModels.ImageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(viewModel: ImageViewModel = hiltViewModel()) {
    val uiState by viewModel.imageUiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_round),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(62.dp)
                            .padding(start = 20.dp)
                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Text(
                        text = "Pic Explorer",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            },

            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFFFFFF)
            ),
            modifier = Modifier.padding(bottom = 20.dp)
        )

        if (uiState.isOffline) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Color.Red),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = "No Internet Connection. Showing cached data.",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 20.dp)
                    )
                    Image(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        modifier = Modifier
                            .clickable { viewModel.getAllImages() },
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

            }
        }

        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            uiState.errorMessage != null -> {
                if (!uiState.isOffline) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(
                                painterResource(R.drawable.error),
                                "error",
                                modifier = Modifier.padding(bottom = 40.dp)
                            )
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
            }

            else -> {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Filter By Author",
                            Modifier.padding(start = 20.dp, bottom = 10.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                        DropDownCommon(
                            label = "All Authors",
                            items = uiState.authors,
                            selected = uiState.selectedAuthor,
                            itemToText = { it ?: "Unknown" },
                            onSelected = { author ->
                                viewModel.filterByAuthor(author)
                            }
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Sort Images",
                            Modifier.padding(start = 20.dp, bottom = 10.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                        DropDownCommon(
                            label = "",
                            items = listOf(SortType.ASCENDING, SortType.DESCENDING),
                            selected = uiState.selectedSort.takeIf { it != SortType.NONE },
                            itemToText = { it.name ?: "" },
                            onSelected = { selected ->
                                viewModel.sortImages(selected ?: SortType.NONE)
                            }
                        )
                    }
                }

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