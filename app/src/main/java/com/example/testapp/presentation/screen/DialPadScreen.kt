package com.example.testapp.presentation.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DialPadScreen() {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Dialer Input
        DialerInputBox(input, onClear = {
            if (input.isNotEmpty()) input = input.dropLast(1)
        })
        //DialPad
        DialPad { digit ->
            input += digit
        }
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
            .fillMaxWidth()
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                spotColor = Color.LightGray
            ),
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
                ),
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DialerInputBox(input: String, onClear: () -> Unit) {
    var hasShown by remember { mutableStateOf(false) }

    val showBox = input.isNotEmpty()

    // When input becomes non-empty for the first time, trigger animation
    LaunchedEffect(showBox) {
        if (showBox && !hasShown) {
            hasShown = true
        }
    }

    // Only animate when input becomes non-empty for the first time
    if (hasShown) {
        AnimatedVisibility(
            visible = showBox,
            enter = fadeIn() + expandVertically(),
            exit = shrinkVertically() + fadeOut()
        ) {
            DialerInputContent(input = input, onClear = onClear)
        }
    } else if (showBox) {
        // Immediate display without animation (before 'hasShown' is set)
        DialerInputContent(input = input, onClear = onClear)
    }
}

@Composable
private fun DialerInputContent(input: String, onClear: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = input,
                    style = typography.headlineMedium.copy(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            IconButton(
                onClick = onClear,
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear Input",
                    tint = Color.Gray
                )
            }
        }
    }
}