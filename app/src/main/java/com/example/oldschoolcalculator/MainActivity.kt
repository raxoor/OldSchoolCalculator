package com.example.oldschoolcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.ToggleButton
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
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
import kotlinx.coroutines.flow.collect
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
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiary
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
            .background(color = MaterialTheme.colorScheme.primary),
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
                calculator = calculator
            )
            CalculatorDisplay(
                modifier = modifier.clip(shape = RoundedCornerShape(5.dp)),
                calculator = calculator
            )
            PrimaryKeypad(
                modifier = modifier.clip(shape = RoundedCornerShape(14.dp)),
                calculator = calculator
            )
            DrawerButton(
                isUp = false,
                modifier = modifier
                    .fillMaxWidth(),
                calculator = calculator
            )
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
            .background(color = MaterialTheme.colorScheme.primary)
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
private fun PrimaryKeypad(
    modifier: Modifier,
    calculator: Calculator
) {
    Box(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .background(color = Color.DarkGray)
    ) {
        Column {
            Row {
                BigButton(text = "7", calculator)
                BigButton(text = "8", calculator)
                BigButton(text = "9", calculator)
                BigButton(text = "X", calculator)
                BigButton(text = "◄", calculator)
            }
            Row {
                BigButton(text = "4", calculator)
                BigButton(text = "5", calculator)
                BigButton(text = "6", calculator)
                BigButton(text = "/", calculator)
                BigButton(text = "CE", calculator)
            }
            Row {
                BigButton(text = "1", calculator)
                BigButton(text = "2", calculator)
                BigButton(text = "3", calculator)
                BigButton(text = "+", calculator)
                BigButton(text = "C", calculator)
            }
            Row {
                BigButton(text = "0", calculator)
                BigButton(text = ".", calculator)
                BigButton(text = "+/-", calculator)
                BigButton(text = "-", calculator)
                BigButton(text = "=", calculator)
            }
        }
    }
}

@Composable
fun BigButton(text: String, calculator: Calculator, modifier: Modifier = Modifier) {
    val defaultButtonGradient = listOf(MaterialTheme.colorScheme.tertiary, Color.DarkGray)
    var buttonGradient by remember { mutableStateOf(defaultButtonGradient) }
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { action ->
            when (action) {
                is PressInteraction.Press -> buttonGradient = defaultButtonGradient.reversed()
                is PressInteraction.Release -> buttonGradient = defaultButtonGradient
                is PressInteraction.Cancel -> buttonGradient = defaultButtonGradient
            }
        }
    }
    ElevatedButton(
        modifier = modifier
            .height(56.dp)
            .width(76.dp)
            .padding(top = 6.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
        onClick = {calculator.buttonPress(text)},
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = Color.White,
            disabledContainerColor = Color.White, //Not used
            disabledContentColor = Color.White, //Not used
        ),
        shape = RoundedCornerShape(10),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 0.dp,
        ),
        interactionSource = interactionSource
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .fillMaxSize()
                .background(brush = Brush.linearGradient(colors = buttonGradient)),
        ) {
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

@Composable
fun DrawerButton(modifier: Modifier = Modifier, isUp: Boolean = true, calculator: Calculator){
    val upIcon = painterResource(R.drawable.up)
    val downIcon = painterResource(R.drawable.down)
    var currentlyDisplayed = if(isUp) upIcon else downIcon
    var state by remember { mutableStateOf(currentlyDisplayed) }
    IconButton(
        modifier = modifier,
        onClick = {
            state = if(state == upIcon) downIcon else upIcon
        },
        colors = IconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White,
            disabledContentColor = Color.White, //Unreachable
            disabledContainerColor = Color.White //Unreachable
        )
        ){
        Icon(
            modifier = modifier.fillMaxSize(),
            painter = state,
            contentDescription = null
        )
    }
}



@Preview(showBackground = false)
@Composable
fun BigButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        BigButton(text = "5", calculator = Calculator())
    }
}

@Preview
@Composable
fun MainButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        Layout()
    }
}

@Preview
@Composable
fun DrawerButtonPreview(){
    OldSchoolCalculatorTheme(
        dynamicColor = false
    ) {
        DrawerButton(calculator = Calculator())
    }
}