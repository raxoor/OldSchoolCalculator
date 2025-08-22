package com.example.oldschoolcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.oldschoolcalculator.ui.theme.OldSchoolCalculatorTheme

@Composable
fun TopDrawer(
    modifier: Modifier = Modifier,
    calculator: Calculator
) {
    var selectedButton by remember { mutableStateOf("DEG") }
    var isShiftActive by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            .background(brush = Brush.linearGradient(colors = listOf(
                Color.LightGray,
                Color.Black
            ), start = Offset.Zero, end = Offset(0.5f, 0.2f), tileMode = TileMode.Mirror))
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                GreenLightButton(
                    text = "DEG",
                    calculator = calculator,
                    selected = selectedButton == "DEG"
                ) { selectedButton = "DEG" }
                GreenLightButton(
                    text = "RAD",
                    calculator = calculator,
                    selected = selectedButton == "RAD"
                ) { selectedButton = "RAD" }
                GreenLightButton(
                    text = "GRA",
                    calculator = calculator,
                    selected = selectedButton == "GRA"
                ) { selectedButton = "GRA" }
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                ShiftButton(text = "Shift") { isShiftActive = !isShiftActive }
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "sin") },
                    shiftDescriptor = {
                        Superscript(
                            text = "sin",
                            superscript = "-1",
                            isOnButton = false
                        )
                    }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "cos") },
                    shiftDescriptor = {
                        Superscript(
                            text = "cos",
                            superscript = "-1",
                            isOnButton = false
                        )
                    }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "tan") },
                    shiftDescriptor = {
                        Superscript(
                            text = "tan",
                            superscript = "-1",
                            isOnButton = false
                        )
                    }
                )
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "x", superscript = "2") },
                    shiftDescriptor = {
                        Superscript(
                            text = "x",
                            superscript = "y",
                            isOnButton = false
                        )
                    }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "√x") },
                    shiftDescriptor = {
                        Superscript(
                            text = "√x",
                            superscript = "y",
                            isOnButton = false,
                            isBefore = true
                        )
                    }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "Log") },
                    shiftDescriptor = {
                        Superscript(
                            text = "10",
                            superscript = "x",
                            isOnButton = false
                        )
                    }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "→°' \"") },
                    shiftDescriptor = { Superscript(text = "°' \"→", isOnButton = false) }
                )
            }
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "1/x") },
                    shiftDescriptor = { Superscript(text = "x!", isOnButton = false) }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "π") },
                    shiftDescriptor = { Superscript(text = "e", isOnButton = false) }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "%") },
                    shiftDescriptor = { Superscript(text = "MOD", isOnButton = false) }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    buttonText = { Superscript(text = "ln") },
                    shiftDescriptor = { Superscript(text = "RNG", isOnButton = false) }
                )
            }
        }
    }
}

@Preview
@Composable
fun TopDrawerPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        TopDrawer(calculator = Calculator())
    }
}

@Composable
fun BottomDrawer(
    modifier: Modifier = Modifier,
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
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row {
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "M+", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "M-", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "MR", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "MC", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "A", modifier = modifier, calculator = calculator)
            }
            Row {
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "B", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "C", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "D", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "E", modifier = modifier, calculator = calculator)
                TextColorButton(onClick = {}, textColor = Colors.BlackButtonLetterBackground, text = "F", modifier = modifier, calculator = calculator)
            }
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                LogicalButton(operation = LogicalOperators.AND, calculator = calculator)
                LogicalButton(operation = LogicalOperators.OR, calculator = calculator)
                LogicalButton(operation = LogicalOperators.XOR, calculator = calculator)
                LogicalButton(operation = LogicalOperators.NOT, calculator = calculator)
            }
            Row {
                LogicalButton(operation = LogicalOperators.NOR, calculator = calculator)
                LogicalButton(operation = LogicalOperators.NAND, calculator = calculator)
                MultiOptionButton(
                    calculator = calculator,
                    options = arrayOf(
                        Option( option = BitWidth.QWORD, textColor = Color.White),
                        Option( option = BitWidth.DWORD, textColor = Color.Yellow),
                        Option( option = BitWidth.WORD, textColor = Color.Magenta),
                        Option( option = BitWidth.BYTE, textColor = Color.Green)
                    ))
                MultiOptionButton(
                    calculator = calculator,
                    options = arrayOf(
                        Option(option = NumberBase.DEC, textColor = Color.White),
                        Option(option = NumberBase.HEX, textColor = Color.Yellow),
                        Option(option = NumberBase.OCT, textColor = Color.Magenta),
                        Option(option = NumberBase.BIN, textColor = Color.Green)

                    ))
            }
        }
    }
}

@Preview
@Composable
fun BottomDrawerPreview(){
    OldSchoolCalculatorTheme(dynamicColor = false) {
        BottomDrawer(calculator = Calculator())
    }
}