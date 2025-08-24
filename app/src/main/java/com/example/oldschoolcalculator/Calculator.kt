package com.example.oldschoolcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.*
import kotlin.random.Random

class Calculator {
    private var displayMode = false
    var input by mutableStateOf(ZERO_STR)
        private set
    private var accumulator: Double = 0.0
    private var memory: Double = 0.0
    private var angleUnits: AngleUnits = AngleUnits.RAD
    private var numBase: NumberBase = NumberBase.DEC
    private var operation: Operation = Operation.ENTER

    companion object {
        private const val ZERO_STR = "0"
        private const val DIGITNUM = 9

        private const val PI = 3.14159
        private const val EULER = 2.71828
        private val MAXINT: Long by lazy {
            var x: Long = 9
            repeat(DIGITNUM - 1) {
                x = x * 10 + 9
            }
            x
        }
    }

    fun <T> buttonPress(button: T) {
        when (button) {
            is Digit -> {
                appendInput(button.symbol)
            }
            is Operation -> execute(button)
            is AngleUnits -> {
                this.input = fitDouble(
                    AngleUnitsConversion.convert(
                        this.input.toDouble(), this.angleUnits, button
                    ).toString()
                )
                this.angleUnits = button
                displayMode = true
            }
            is NumberBase -> {
                this.numBase = button
                numBaseOutputSelector()
                displayMode = true
            }
            is BitWidth -> {/*TODO:*/
            }
            else -> {
                error("Undefined type passed from frontend")
            }
        }
    }

    fun appendInput(symbol: Char) {
        if (this.input == ZERO_STR || displayMode || this.input == "-0") {
            this.input = "" + symbol
            displayMode = false
        } else if (symbol == '.' && input.contains(symbol)) return
        else if (input.length < DIGITNUM)
            this.input += symbol
    }

    fun clearAll() {
        this.input = ZERO_STR
        this.accumulator = 0.0
        this.displayMode = false
    }

    fun numBaseOutputSelector() {
        val num: Double = input.toDoubleOrNull()?:return
        if(num % 1.0 == 0.0) {
            val value: Int = num.toInt()
            val format: String = when (this.numBase) {
                NumberBase.DEC -> NumberBase.DEC.symbol
                NumberBase.HEX -> NumberBase.HEX.symbol
                NumberBase.OCT -> NumberBase.OCT.symbol
                NumberBase.BIN -> NumberBase.BIN.symbol
            }
            input = String.format(format, value)

        }
    }

    fun execute(incoming: Operation) {

        val number = when(this.numBase){
            NumberBase.DEC -> input.toDoubleOrNull() ?: 0.0
            NumberBase.HEX -> input.toInt(16).toDouble()
            NumberBase.OCT -> input.toInt(8).toDouble()
            NumberBase.BIN -> input.toInt(2).toDouble()
        }

        if (incoming in Operation.immediate) {
            when (incoming) {
                Operation.BACKSPACE -> input = input.dropLast(1)
                Operation.CLEAR -> {
                    clearAll(); return
                }

                Operation.CLEAR_CURRENT -> input = ZERO_STR
                Operation.CHANGE_SIGN -> input =
                    if (input[0] == '-') input.substring(1) else "-$input"

                Operation.MEMORY_ADD -> {
                    memory += number; displayMode = true
                }

                Operation.MEMORY_SUBTRACT -> {
                    memory -= number; displayMode = true
                }

                Operation.MEMORY_READ -> {
                    input = memory.toString()
                }

                Operation.MEMORY_CLEAR -> {
                    memory = 0.0
                }

                Operation.SIN -> {
                    input = fitDouble(sin(number).toString())
                }

                Operation.COS -> {
                    input = fitDouble(cos(number).toString())
                }

                Operation.TAN -> {
                    input = fitDouble(tan(number).toString())
                }

                Operation.ARCSIN -> {
                    input = fitDouble(asin(number).toString())
                }

                Operation.ARCCOS -> {
                    input = fitDouble(acos(number).toString())
                }

                Operation.ARCTAN -> {
                    input = fitDouble(atan(number).toString())
                }

                Operation.SQUARED -> {
                    input = fitDouble(number.pow(2.0).toString())
                }

                Operation.SQUARE_ROOT -> {
                    input = fitDouble(sqrt(number).toString())
                }

                Operation.LOG -> {
                    input = fitDouble(log2(number).toString())
                }

                Operation.ANTI_LOG -> {
                    input = fitDouble(10.0.pow(number).toString())
                }

                Operation.RECIPROCAL -> {
                    input = fitDouble((1.0 / number).toString())
                }

                Operation.FACTORIAL -> {
                    input = fitDouble(factorial(number.toInt()).toString())
                }

                Operation.PI -> {
                    input = PI.toString()
                }

                Operation.EULER -> {
                    input = EULER.toString()
                }

                Operation.PERCENT -> {
                    input = fitDouble((accumulator * (number / 100.0)).toString())
                }

                Operation.RNG -> {
                    input = fitDouble(Random.Default.nextDouble().toString())
                }

                Operation.NAT_LOG -> {
                    input = fitDouble(log(number, EULER).toString())
                }

                else -> error("Unreachable branch triggered in execute")
            }
        } else {
            when (this.operation) {
                Operation.ENTER -> accumulator = number
                Operation.ADD -> accumulator += number
                Operation.SUBTRACT -> accumulator -= number
                Operation.MULTIPLY -> accumulator *= number
                Operation.DIVIDE -> accumulator /= number
                Operation.POWER -> accumulator = accumulator.pow(number)
                Operation.ROOT -> accumulator = accumulator.pow((1.0 / number))
                Operation.MODULO -> accumulator = accumulator % number
                else -> error("Unreachable branch triggered in execute")
            }
            this.operation = incoming
            this.displayMode = true
            this.input = fitDouble(accumulator.toString())
        }
        numBaseOutputSelector()
    }

    /**Returns a string that fits within the desired screen digits, or enters Error state*/
    private fun fitDouble(numStr: String): String {
        val num = numStr.toDoubleOrNull() ?: 0.0
        var str = numStr
        if (num % 1 == 0.0) {
            if (num.absoluteValue > MAXINT) {
                return errorOut()
            }
        } else {
            if (str.indexOf('.') > DIGITNUM - 2) {
                return errorOut()
            }
            if (str.length > DIGITNUM) str = str.substring(0, DIGITNUM)
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

