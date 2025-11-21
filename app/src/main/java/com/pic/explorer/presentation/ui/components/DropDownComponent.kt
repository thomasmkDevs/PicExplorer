package com.pic.explorer.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AuthorDropDown(
    authors: List<String?>,
    selected: String?,
    onSelected: (String?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(45.dp)
            .shadow(5.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { expanded = true },
        contentAlignment = Alignment.Center) {
        Text(
            text = selected ?: "All Authors",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
                .background(Color.White)
        ) {
            DropdownMenuItem(
                onClick = {
                    onSelected(null)
                    expanded = false
                },
                text = {
                    Text(
                        text = "All Authors",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                })

            authors.forEach { author ->
                DropdownMenuItem(
                    onClick = {
                        onSelected(author)
                        expanded = false
                    },
                    text = {
                        if (author != null) {
                            Text(
                                text = author,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    })
            }
        }
    }

}