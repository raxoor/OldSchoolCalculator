package com.example.oldschoolcalculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    text: String,
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
            onClick = { calculator.buttonPress(text = text); onClick() },
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
        Text(
            modifier = modifier
                .fillMaxSize(),
            text = text,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
fun NumpadButton(text: String, calculator: Calculator, modifier: Modifier = Modifier) {
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
        onClick = { calculator.buttonPress(text) },
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
fun ShiftableButton(
    modifier: Modifier = Modifier,
    text: String,
    calculator: Calculator,
    isActive: Boolean
) {
    Column(
        modifier = modifier
            .height(56.dp)
            .width(76.dp)
            .padding(bottom = 6.dp, start = 4.dp, end = 4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.End
        ) {
            val writingColor = Colors.DarkYellow
            Spacer(
                modifier = modifier.weight(0.1f)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.displayLarge,
                fontSize = 14.sp,
                color = writingColor
            )
            Text(
                text = "-1",
                style = MaterialTheme.typography.displayLarge,
                fontSize = 10.sp,
                color = writingColor
            )
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            ElevatedButton(
                modifier = modifier.fillMaxWidth(),
                onClick = { calculator.buttonPress(if (isActive) "$text-1" else text) },
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
            Text(
                text = text,
                style = MaterialTheme.typography.displayLarge,
                fontSize = 26.sp,
                color = Color.Black
            )
        }

    }
}

@Composable
fun ShiftButton(
    modifier: Modifier = Modifier,
    text: String,
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
            text = text,
            style = MaterialTheme.typography.displayLarge,
            fontSize = 26.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
        )
    }


}


@Preview(showBackground = false)
@Composable
fun BigButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        NumpadButton(text = "5", calculator = Calculator())
    }
}

@Preview(showBackground = false)
@Composable
fun ColorRadioButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        GreenLightButton(selected = true, calculator = Calculator(), text = "DEG") {}
    }
}

@Preview(showBackground = false)
@Composable
fun ShiftableButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        ShiftableButton(calculator = Calculator(), text = "cos", isActive = true)
    }
}

@Preview(showBackground = false)
@Composable
fun ShiftButtonPreview() {
    OldSchoolCalculatorTheme(dynamicColor = false) {
        ShiftButton(text = "Shift", onClick = {})
    }
}

