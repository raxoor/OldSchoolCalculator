package com.example.oldschoolcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oldschoolcalculator.ui.theme.OldSchoolCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OldSchoolCalculatorTheme(
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.DarkGray
                ) {
                    Layout()
                }
            }
        }
    }
}


@Composable
fun Layout(modifier: Modifier = Modifier) {
    val calculator = remember { Calculator() }

    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color.Black),
        contentAlignment = Alignment.Center

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            DrawerButton(
                modifier = modifier
                    .fillMaxWidth(),
            ) {
                TopDrawer(
                    modifier = modifier.clip(shape = RoundedCornerShape(14.dp)),
                    calculator = calculator
                )
            }
            CalculatorDisplay(
                modifier = modifier.clip(shape = RoundedCornerShape(5.dp)),
                calculator = calculator
            )
            Keypad(
                modifier = modifier.clip(shape = RoundedCornerShape(14.dp)),
                calculator = calculator
            )
            DrawerButton(
                isUp = false,
                modifier = modifier
                    .fillMaxWidth(),
            )
            {

                BottomDrawer(
                    //modifier = modifier.clip(shape = RoundedCornerShape(14.dp)),
                    calculator = calculator
                )
            }
        }
    }
}

@Composable
private fun CalculatorDisplay(
    modifier: Modifier,
    calculator: Calculator
) {
    Box(
        modifier = modifier
            .width(400.dp)
            .background(color = Color.Black)
            .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)

    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = modifier
                .background(color = Color.White)
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)

        ) {
            Box(
                contentAlignment = Alignment.CenterEnd,
                modifier = modifier
                    .fillMaxWidth()
                    .background(color = Color.LightGray)
                    .wrapContentHeight()
                    .padding(top = 8.dp, bottom = 8.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "888888888888",
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.digital_display)),
                    fontSize = 60.sp,
                    color = Colors.DisplayBurnIn
                )
                Text(
                    text = calculator.input,
                    textAlign = TextAlign.Left,
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.digital_display)),
                    fontSize = 60.sp,
                    color = Color.Black

                )
            }
        }
    }
}

@Composable
private fun Keypad(
    modifier: Modifier,
    calculator: Calculator
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.DarkGray,
                        Color.LightGray
                    ), start = Offset.Zero, end = Offset(0.5f, 0.2f), tileMode = TileMode.Mirror
                )
            )
        //.background(color = Color.DarkGray)
    ) {
        Column {
            for (row in ButtonLayout.main) {
                Row {
                    for (col in row)
                        NumpadButton(text = col, calculator = calculator)
                }
            }
        }
    }
}




@Preview
@Composable
fun MainButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        Layout()
    }
}




