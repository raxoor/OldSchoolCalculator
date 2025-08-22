package com.example.oldschoolcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oldschoolcalculator.ui.theme.OldSchoolCalculatorTheme

@Composable
fun Header(
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    )
    {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.Black),
            contentAlignment = Alignment.BottomCenter

        ) {
            Text(
                text = stringResource(R.string.header),
                fontFamily = FontFamily(Font(R.font.goldman_bold)),
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Black, Color.Transparent)
                    )
                ),
        )
    }
}
@Composable
fun Background(
    modifier: Modifier = Modifier,
){
    Box(
        modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.inversePrimary),
                    start = Offset(x = 0.0f, y = 0.0f),
                    end = Offset(x= 300.0f, y = 300.0f),
                    tileMode = TileMode.Mirror
                ))
    )
}

@Preview
@Composable
fun BackgroundPreview(){
    OldSchoolCalculatorTheme(
        dynamicColor = true
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.DarkGray
        ) {
            Background()
            Header()
        }
    }
}
