package com.example.oldschoolcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.math.BigDecimal
import kotlin.math.absoluteValue

class Calculator {
    private var displayMode = false
    var input by mutableStateOf(ZERO)
        private set
    private var accumulator: Double = 0.0
    private var operation: Operation = Operation.ENTER

    companion object {
        private const val ZERO = "0"
        private const val DIGITNUM = 11
        private val MAXINT: Long by lazy {
            var x: Long = 9
            repeat(DIGITNUM - 1) {
                x = x * 10 + 9
            }
            x
        }
    }

    fun buttonPress(text: String) {
        if (text.length == 1 && (text[0].isDigit() || text[0] == '.')) appendInput(text[0])
        else {
            execute(Operation.fromSymbol(text))
        }
    }

    fun appendInput(symbol: Char) {
        if (this.input == ZERO || displayMode || this.input == "-0") {
            this.input = "" + symbol; displayMode = false
        } else if (symbol == '.' && input.contains(symbol)) return
        else if (input.length < DIGITNUM)
            this.input += symbol
    }

    fun clearAll() {
        this.input = ZERO
        this.accumulator = 0.0
        this.displayMode = false
    }

    fun execute(incoming: Operation) {
        val number: Double = input.toDoubleOrNull() ?: 0.0
        if (incoming in Operation.imediate) {
            when (incoming) {  //Leave as is until final version
                Operation.BACKSPACE -> input = input.dropLast(1)
                Operation.CLEAR -> {
                    clearAll(); return
                }
                Operation.CLEAR_CURRENT -> input = ZERO
                Operation.CHANGE_SIGN -> input = if(input[0] == '-') input.substring(1) else "-$input"
                else -> error("Unreachable branch triggered in execute")
            }
        } else {
            when (this.operation) {
                Operation.ENTER -> accumulator = number
                Operation.ADD -> accumulator += number
                Operation.SUBTRACT -> accumulator -= number
                Operation.MULTIPLY -> accumulator *= number
                Operation.DIVIDE -> accumulator /= number
                else -> error("Unreachable branch triggered in execute")
            }
            this.operation = incoming
            this.displayMode = true
            this.input = fitDouble()
        }

    }

    /**Returns a string that fits within the desired screen digits, or enters Error state*/
    private fun fitDouble(): String {
        var str = BigDecimal(accumulator).toPlainString()
        if (accumulator % 1 == 0.0) {
            if (accumulator.absoluteValue > MAXINT) {
                return errorOut()
            }
        } else {
            if (str.indexOf('.') > DIGITNUM - 2) {
                return errorOut()
            }
            str = str.substring(0, DIGITNUM)
        }
        return str
    }

    /**Resets the calculator and returns Error string*/
    private fun errorOut(): String {
        execute(Operation.CLEAR)
        displayMode = true
        return "Error"
    }

}

enum class Operation(val symbol: String) {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("X"),
    DIVIDE("/"),
    CLEAR("C"),
    CLEAR_CURRENT("CE"),
    CHANGE_SIGN("+/-"),
    BACKSPACE("◄"),
    ENTER("=");

    companion object {
        fun fromSymbol(symbol: String): Operation {
            for (op in Operation.entries) {
                if (symbol == op.symbol) {
                    return op
                }
            }
            return ENTER
        }

        val imediate = listOf(BACKSPACE, CHANGE_SIGN, CLEAR_CURRENT, CLEAR)
    }
}