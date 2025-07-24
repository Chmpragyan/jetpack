package com.example.testapp.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialPadScreen() {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        //DialPad
        DialPad { digit ->
            input += digit
        }
    }

    // Backspace or Clear
    Button(
        onClick = {
            if (input.isNotEmpty()) input = input.dropLast(1)
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Delete")
    }
}

@Composable
fun DialPad(onKeyPress: (String) -> Unit) {
    val dialPadButtons = listOf(
        Triple("1", " ", ""),
        Triple("2", "ABC", ""),
        Triple("3", "DEF", ""),
        Triple("4", "GHI", ""),
        Triple("5", "JKL", ""),
        Triple("6", "MNO", ""),
        Triple("7", "PQRS", ""),
        Triple("8", "TUV", ""),
        Triple("9", "WXYZ", ""),
        Triple("*", "", ""),
        Triple("0", "+", ""),
        Triple("#", "", "")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth(),
        userScrollEnabled = false
    ) {
        items(dialPadButtons.size) { index ->
            val (number, letters, _) = dialPadButtons[index]
            DialPadButton(number, letters, onClick = { onKeyPress(number) })
        }
    }
}

@Composable
fun DialPadButton(number: String, letters: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .padding(12.dp)
            .size(80.dp), contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = number,
                style = typography.headlineMedium.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            )
            if (letters.isNotEmpty()) {
                Text(
                    text = letters,
                    style = typography.headlineMedium.copy(
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                )
            }
        }


    }
}