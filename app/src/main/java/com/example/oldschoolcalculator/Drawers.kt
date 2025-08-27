package com.example.oldschoolcalculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
fun VerticalLine(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .background(color = Color.Black)
    ) {}
}
@Composable
fun TopDrawer(
    modifier: Modifier = Modifier,
    calculator: Calculator
) {
    var selectedButton by remember { mutableStateOf(AngleUnits.RAD) }
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
                    option = AngleUnits.DEG,
                    calculator = calculator,
                    selected = selectedButton == AngleUnits.DEG
                ) { selectedButton = AngleUnits.DEG }
                GreenLightButton(
                    option = AngleUnits.RAD,
                    calculator = calculator,
                    selected = selectedButton == AngleUnits.RAD
                ) { selectedButton = AngleUnits.RAD }
                GreenLightButton(
                    option = AngleUnits.GRAD,
                    calculator = calculator,
                    selected = selectedButton == AngleUnits.GRAD
                ) { selectedButton = AngleUnits.GRAD }

            }

            VerticalLine()
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                ShiftButton { isShiftActive = !isShiftActive }
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    primary = Operation.SIN,
                    secondary = Operation.ARCSIN,
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
                    primary = Operation.COS,
                    secondary = Operation.ARCCOS,
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
                    primary = Operation.TAN,
                    secondary = Operation.ARCTAN,
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
                    primary = Operation.SQUARED,
                    secondary = Operation.POWER,
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
                    primary = Operation.SQUARE_ROOT,
                    secondary = Operation.ROOT,
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
                    primary = Operation.LOG,
                    secondary = Operation.ANTI_LOG,
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
                    primary = Operation.TO_DEG,
                    secondary = Operation.FROM_DEG,
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
                    primary = Operation.RECIPROCAL,
                    secondary = Operation.FACTORIAL,
                    buttonText = { Superscript(text = "1/x") },
                    shiftDescriptor = { Superscript(text = "x!", isOnButton = false) }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    primary = Operation.PI,
                    secondary = Operation.EULER,
                    buttonText = { Superscript(text = "π") },
                    shiftDescriptor = { Superscript(text = "e", isOnButton = false) }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    primary = Operation.PERCENT,
                    secondary = Operation.MODULO,
                    buttonText = { Superscript(text = "%") },
                    shiftDescriptor = { Superscript(text = "MOD", isOnButton = false) }
                )
                ShiftableButton(
                    modifier = modifier,
                    calculator = calculator,
                    isActive = isShiftActive,
                    primary = Operation.NAT_LOG,
                    secondary = Operation.RNG,
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

fun loop(current: Int, len: Int): Int {
    return if (current == len - 1) 0
    else current + 1
}

@Composable
fun BottomDrawer(
    modifier: Modifier = Modifier,
    calculator: Calculator
) {

    var bitWidthState by remember { mutableIntStateOf(0) }
    var numBaseState by remember { mutableIntStateOf(0) }
    val bitWidth = arrayOf(BitWidth.DWORD, BitWidth.WORD, BitWidth.BYTE)
    val numBase = arrayOf(NumberBase.DEC, NumberBase.HEX, NumberBase.OCT)
    val hexButtonsEnabled = numBase[numBaseState] == NumberBase.HEX

    var numberInMemory by remember { mutableStateOf(false) }

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
                TextColorButton(calculator = calculator, onClick = {numberInMemory = true}, enabled = true, option = Operation.MEMORY_ADD, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, onClick = {numberInMemory = true}, enabled = true, option = Operation.MEMORY_SUBTRACT, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, enabled = numberInMemory, option = Operation.MEMORY_READ, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, onClick = {numberInMemory = false}, enabled = numberInMemory, option = Operation.MEMORY_CLEAR, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, enabled = hexButtonsEnabled, option = Digit.A, modifier = modifier.weight(1f))
            }
            Row {
                TextColorButton(calculator = calculator, enabled = hexButtonsEnabled, option = Digit.B, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, enabled = hexButtonsEnabled, option = Digit.C, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, enabled = hexButtonsEnabled, option = Digit.D, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, enabled = hexButtonsEnabled, option = Digit.E, modifier = modifier.weight(1f))
                TextColorButton(calculator = calculator, enabled = hexButtonsEnabled, option = Digit.F, modifier = modifier.weight(1f))
            }
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                LogicalButton(operation = Operation.AND, calculator = calculator, modifier = modifier.weight(1f))
                LogicalButton(operation = Operation.OR, calculator = calculator, modifier = modifier.weight(1f))
                LogicalButton(operation = Operation.XOR, calculator = calculator, modifier = modifier.weight(1f))
                LogicalButton(operation = Operation.NOT, calculator = calculator, modifier = modifier.weight(1f))
            }
            Row {

                LogicalButton(operation = Operation.NOR, calculator = calculator, modifier = modifier.weight(1f))
                LogicalButton(operation = Operation.NAND, calculator = calculator, modifier = modifier.weight(1f))
                SelectorButton(
                    modifier = modifier.weight(1f),
                    option = bitWidth[bitWidthState],
                    onClick = { bitWidthState = loop(bitWidthState, bitWidth.size); calculator.buttonPress(bitWidth[bitWidthState]) },)
                SelectorButton(
                    modifier = modifier.weight(1f),
                    option = numBase[numBaseState],
                    onClick = { numBaseState = loop(numBaseState, numBase.size); calculator.buttonPress(numBase[numBaseState]) },)
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