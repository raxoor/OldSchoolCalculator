package com.example.oldschoolcalculator

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oldschoolcalculator.ui.theme.OldSchoolCalculatorTheme

@Composable
fun DrawerButton(
    modifier: Modifier = Modifier,
    isUp: Boolean = true,
    content: @Composable (() -> Unit) = {},
) {
    var isOpen by remember { mutableStateOf(false) }
    val upIcon = painterResource(R.drawable.up)
    val downIcon = painterResource(R.drawable.down)
    var state by remember { mutableStateOf(isUp) }
    if (!isUp) {
        AnimatedVisibility(
            visible = isOpen,
            enter = slideInVertically { height -> -height } + expandVertically(expandFrom = Alignment.Top),
            exit = slideOutVertically { height -> -height } + shrinkVertically(shrinkTowards = Alignment.Top)
        ) {
            content()
        }
    }
    IconButton(
        modifier = modifier,
        onClick = {
            state = !state
            isOpen = !isOpen
        },
        colors = IconButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContentColor = Color.White, //Unreachable
            disabledContainerColor = Color.White //Unreachable
        )
    ) {
        Icon(
            modifier = modifier.fillMaxSize(),
            painter = if (state) upIcon else downIcon,
            contentDescription = null
        )
    }

    if (isUp) {
        AnimatedVisibility(
            visible = isOpen,
            enter = slideInVertically { height -> height } + expandVertically(),
            exit = slideOutVertically { height -> height } + shrinkVertically()

        ) {
            content()
        }
    }
}

@Composable
fun GreenLightButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    option: AngleUnits,
    calculator: Calculator,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .width(120.dp)
            .padding(top = 8.dp, bottom = 10.dp, start = 4.dp, end = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedButton(
            modifier = modifier.fillMaxSize(),
            onClick = { calculator.buttonPress(option); onClick() },
            colors = ButtonColors(
                contentColor = Color.Black,
                containerColor = Color.LightGray,
                disabledContentColor = Color.Transparent, // Not Used
                disabledContainerColor = Color.Transparent
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 0.dp,
            )
        ) {
        }

        AnimatedVisibility(
            modifier = modifier.clip(RoundedCornerShape(20.dp)),
            visible = selected,
            enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
            exit = fadeOut()
        ) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .graphicsLayer(scaleX = 3.6f, scaleY = 1.6f)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.Green, Color.Transparent),
                        )
                    )
            )
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = modifier
                    .fillMaxSize()
                    .offset(y = 2.dp),
                text = option.toString(),
                style = MaterialTheme.typography.displayLarge,
                fontSize = 32.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
fun <T>NumpadButton(
    option: T,
    calculator: Calculator,
    modifier: Modifier = Modifier
) {
    val defaultButtonGradient = listOf(Colors.LightGray, Color.DarkGray)
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
        onClick = { calculator.buttonPress(option) },
        colors = ButtonColors(
            containerColor = Colors.LightGray,
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
        ) {
            Text(
                text = when(option){is Digit -> option.symbol.toString(); is Operation -> option.symbol; else -> error("Invalid type in NumpadButton")},
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
        }
    }
}

/**Used only for shiftable buttons. Use the before flag to toggle
 * superscripts position, isOnButton will but the text on the button or descriptor*/
@Composable
fun Superscript(
    modifier: Modifier = Modifier,
    text: String,
    superscript: String = "",
    isBefore: Boolean = false,
    isOnButton: Boolean = true
) {

    val fontSize = if (isOnButton) 26.sp else 14.sp
    val fontDescaler = if (isOnButton) 10 else 4
    val writingColor = if (isOnButton) Color.Black else Colors.DarkYellow
    val upper: @Composable () -> Unit =
        {
            Text(
                text = superscript,
                style = MaterialTheme.typography.displayLarge,
                fontSize = (fontSize.value - fontDescaler).sp,
                color = writingColor
            )
        }

    val main: @Composable () -> Unit =
        {
            Text(
                text = text,
                style = MaterialTheme.typography.displayLarge,
                fontSize = fontSize,
                color = writingColor
            )
        }
    Row(
        horizontalArrangement = Arrangement.End
    ) {
        if (!isOnButton) {
            Spacer(
                modifier = modifier.weight(0.1f)
            )
        }
        if (isBefore) {
            upper()
            main()
        } else {
            main()
            upper()
        }
    }
}

@Composable
fun ShiftableButton(
    modifier: Modifier = Modifier,
    calculator: Calculator,
    primary: Operation,
    secondary: Operation,
    isActive: Boolean,
    buttonText: @Composable () -> Unit,
    shiftDescriptor: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .height(56.dp)
            .width(76.dp)
            .padding(bottom = 6.dp, start = 4.dp, end = 4.dp),
    ) {
        shiftDescriptor()
        Box(
            contentAlignment = Alignment.Center
        ) {
            ElevatedButton(
                modifier = modifier.fillMaxWidth(),
                onClick = { if (isActive) calculator.buttonPress(secondary) else calculator.buttonPress(primary)},
                colors = ButtonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent, //Not used
                    disabledContentColor = Color.Transparent, //Not used
                ),
                shape = RoundedCornerShape(10),
                contentPadding = PaddingValues(0.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 0.dp,
                )
            ) {}
            androidx.compose.animation.AnimatedVisibility(
                modifier = modifier.clip(RoundedCornerShape(2.dp)),
                visible = isActive,
                enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessHigh)),
                exit = fadeOut()
            )
            {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .graphicsLayer(scaleX = 3.6f, scaleY = 1.6f)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    Colors.DarkYellow,
                                    Color.Transparent
                                )
                            )
                        )
                )

            }
            buttonText()
        }

    }
}

@Composable
fun ShiftButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    ElevatedButton(
        modifier = modifier
            .size(width = 88.dp, height = 54.dp)
            .padding(top = 10.dp, bottom = 4.dp),
        contentPadding = PaddingValues(top = 4.dp, bottom = 0.dp, start = 0.dp, end = 0.dp),
        onClick = onClick,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 0.dp,
        ),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonColors(
            contentColor = Color.White,
            containerColor = Colors.DarkYellow,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )

    ) {
        Text(
            modifier = modifier
                .fillMaxSize(),
            text = "Shift",
            style = MaterialTheme.typography.displayLarge,
            fontSize = 26.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }


}

@Composable
fun <T>TextColorButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    calculator: Calculator,
    option: T,
    enabled: Boolean = false,
) {

    ElevatedButton(
        enabled = enabled,
        modifier = modifier
            .height(56.dp)
            .width(76.dp)
            .padding(top = 6.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
        onClick = { calculator.buttonPress(option) },
        shape = RoundedCornerShape(10),
        colors = ButtonColors(
            contentColor = Color.White,
            containerColor = Color.Black,
            disabledContentColor = Colors.BlackButtonLetterBackground,
            disabledContainerColor = Color.Black
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 0.dp
        ),


        ) {
        Text(
            modifier = modifier,
            text = if(option is Operation)option.symbol else if(option is Digit)option.symbol.toString() else error("Unsupported option: type in TextColorButton"),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center
            )
    }

}

@Composable
fun LogicalButton(
    modifier: Modifier = Modifier,
    calculator: Calculator,
    operation: Operation,
) {

    ElevatedButton(
        modifier = modifier
            .height(56.dp)
            .width(96.dp)
            .padding(top = 6.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
        onClick = {calculator.buttonPress(operation)},
        shape = RoundedCornerShape(10),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 0.dp
        )

    ) {
        Text(
            text = operation.toString(),
            style = MaterialTheme.typography.displayLarge,
            fontSize = 22.sp,
            maxLines = 1,
        )
    }
}

@Composable
fun <T> SelectorButton(
    modifier: Modifier = Modifier,
    option: T,
    onClick: () -> Unit
) {


    Box(
        modifier = modifier
            .height(56.dp)
            .width(96.dp)
            .background(
                brush = Brush.verticalGradient(
                    startY = 0.0f,
                    endY = 0.342f,
                    colors = listOf(Color.White, Color.Black),
                    tileMode = TileMode.Repeated
                )

            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 6.dp, bottom = 6.dp, start = 4.dp, end = 4.dp),
            onClick = onClick,
            shape = RoundedCornerShape(35),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
                disabledContentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            )
        ) {
            AnimatedContent(
                targetState = option,
                transitionSpec = {
                    slideInVertically { -it } togetherWith slideOutVertically { it }
                }
            ) { option ->
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option.toString(),
                        style = MaterialTheme.typography.displayLarge,
                        fontSize = 16.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun MultipleOptionButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        SelectorButton<BitWidth>(
            option = BitWidth.QWORD,
            onClick = {}
        )

    }
}

@Preview
@Composable
fun LogicalButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        LogicalButton(calculator = Calculator(), operation = Operation.NAND)
    }
}

@Preview(showBackground = false)
@Composable
fun BigButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        NumpadButton(option = Digit.FIVE, calculator = Calculator())
    }
}

@Preview(showBackground = false)
@Composable
fun ColorRadioButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        GreenLightButton(selected = true, calculator = Calculator(), option = AngleUnits.GRAD) {}
    }
}

@Preview(showBackground = false)
@Composable
fun ShiftableButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        ShiftableButton(
            calculator = Calculator(),
            isActive = true,
            primary = Operation.SIN,
            secondary = Operation.ARCSIN,
            buttonText = { Superscript(text = "sin", superscript = "-1") },
        ) { Superscript(text = "sin", superscript = "-1", isOnButton = false) }
    }
}

@Preview(showBackground = false)
@Composable
fun ShiftButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        ShiftButton(onClick = {})
    }
}

@Preview(showBackground = false)
@Composable
fun TextColorButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        TextColorButton(
            option = Operation.MEMORY_READ,
            calculator = Calculator()
        )
    }
}

