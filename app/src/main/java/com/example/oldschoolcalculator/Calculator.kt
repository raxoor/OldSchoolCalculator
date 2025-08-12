package com.example.oldschoolcalculator

import android.health.connect.datatypes.units.Length
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.math.BigDecimal
import kotlin.math.absoluteValue

class Calculator {
    private var displayMode = false
    var input by mutableStateOf(ZERO)
        private set
    private var accumulator: Double = 0.0
    private var operation = Operation.ENTER
        set(value) {
            if (value == Operation.CLEAR) {
                field = value
                execute()
                field = Operation.ENTER
            } else {
                execute()
                field = value
            }
        }

    companion object {
        private const val ZERO = "0"
        private const val DIGITNUM = 10
        private val MAXINT: Long by lazy {
            var x: Long = 9
            for (i in 0 until DIGITNUM-1) {
                x = x * 10 + 9
            }
            x
        }
    }

    fun buttonPress(text: String) {
        if (text.length == 1 && (text[0].isDigit() || text[0] == '.')) appendInput(text[0])
        else this.operation = Operation.fromSymbol(text)
    }

    fun appendInput(symbol: Char) {
        if (this.input == ZERO || displayMode) {
            this.input = "" + symbol; displayMode = false
        } else if (input.length < DIGITNUM) this.input += symbol
    }

    fun clearAll() {
        this.input = ZERO
        this.accumulator = 0.0
        this.displayMode = false
    }
    
    fun execute() {
        when (this.operation) {
            Operation.ENTER -> accumulator = input.toDouble()
            Operation.ADD -> accumulator += input.toDouble()
            Operation.SUBTRACT -> accumulator -= input.toDouble()
            Operation.MULTIPLY -> accumulator *= input.toDouble()
            Operation.DIVIDE -> accumulator /= input.toDouble()
            Operation.CLEAR -> {
                clearAll(); return;
            }
        }
        this.displayMode = true
        this.input = fitDouble()
    }

    /**Returns a string that fits within the desired screen digits, or enters Error state*/
    private fun fitDouble(): String {
        var str = BigDecimal(accumulator).toPlainString()
        if (accumulator % 1 == 0.0) {
            if (accumulator.absoluteValue > MAXINT) {
                return  errorOut()
            }
        } else {
            if(str.indexOf('.') > DIGITNUM - 2) {
                return errorOut()
            }
            str =  str.substring(0, DIGITNUM)
        }
        return str
    }

    /**Resets the calculator and returns Error string*/
    private fun errorOut(): String{
        this.operation = Operation.CLEAR
        displayMode = true
        return "Error"
    }

}

enum class Operation(val symbol: String) {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("X"),
    DIVIDE("/"),
    CLEAR("CE"),
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
    }
}