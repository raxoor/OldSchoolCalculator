package com.example.oldschoolcalculator

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oldschoolcalculator.ui.theme.OldSchoolCalculatorTheme
import java.nio.file.WatchEvent

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
                    color = MaterialTheme.colorScheme.primary
                ) {
                    MainButtonLayout()
                }
            }
        }
    }
}


@Composable
fun DisplayRender(text: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .wrapContentHeight()
            .padding(top = 4.dp, bottom = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.LightGray)
                .wrapContentHeight()
                .padding(top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Left,
                maxLines = 1,
                fontFamily = FontFamily(Font(R.font.digital_display)),
                fontSize = 60.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
fun MainButtonLayout(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(color = Color.DarkGray)

    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Box(
                modifier = modifier.background(color = MaterialTheme.colorScheme.primary)
            ) {
                DisplayRender(
                    "123456789",
                    modifier.padding(start = 24.dp, end = 24.dp, top = 8.dp, bottom = 8.dp)
                )
            }
            Row {
                BigButton(text = "7")
                BigButton(text = "8")
                BigButton(text = "9")
                BigButton(text = "X")
                BigButton(text = "M+")
            }
            Row {
                BigButton(text = "4")
                BigButton(text = "5")
                BigButton(text = "6")
                BigButton(text = "/")
                BigButton(text = "MR")
            }
            Row {
                BigButton(text = "1")
                BigButton(text = "2")
                BigButton(text = "3")
                BigButton(text = "+")
                BigButton(text = "CE")
            }
            Row {
                BigButton(text = "0")
                BigButton(text = ".")
                BigButton(text = "+/-")
                BigButton(text = "-")
                BigButton(text = "=")
            }
        }
    }
}

@Composable
fun BigButton(modifier: Modifier = Modifier, text: String) {
    ElevatedButton(
        modifier = modifier
            .height(56.dp)
            .width(76.dp)
            .padding(top = 6.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
        onClick = {/*TODO*/ },
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White,
            disabledContainerColor = Color.White, //TODO: lol
            disabledContentColor = Color.White, //TODO: lol
        ),
        shape = RoundedCornerShape(10),
        contentPadding = PaddingValues(0.dp),

        ) {
        Text(
            text = text,
            color = Color.White,
            style = MaterialTheme.typography.displayLarge
        )

    }
}


@Preview(showBackground = false)
@Composable
fun BigButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        BigButton(text = "5")
    }
}

@Preview
@Composable
fun MainButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        MainButtonLayout()
    }
}

@Preview
@Composable
fun DisplayRendererPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        DisplayRender("123456789")
    }
}