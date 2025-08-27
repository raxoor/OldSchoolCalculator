package com.example.oldschoolcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.*
import kotlin.random.Random

class Calculator {
    private var displayMode = false
    var display: String by mutableStateOf(ZERO_STR)
        private set

    private var accumulator: Double = 0.0
    private var memory: Double = 0.0
    private var angleUnits: AngleUnits = AngleUnits.RAD
    private var numBase: NumberBase = NumberBase.DEC
    private var bitWidth: BitWidth = BitWidth.DWORD
    private var operation: Operation = Operation.ENTER

    companion object {
        private const val ZERO_STR = "0"
        private const val DIGITNUM = 10

        private const val PI = 3.14159
        private const val EULER = 2.71828
        private val MAXDISPLAYABLE: Long by lazy {
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
                if(this.numBase != NumberBase.DEC) {
                    this.display = errorOut()
                    return
                }
                this.display = fitDouble(
                    AngleUnitsConversion.convert(
                        this.display.toDouble(), this.angleUnits, button
                    ).toString()
                )
                this.angleUnits = button
                displayMode = true
            }
            is NumberBase -> {
                this.display = displayToNumber().toString()
                this.numBase = button
                outputFormater()
                displayMode = true
            }
            is BitWidth -> {
                this.display = displayToNumber().toString()
                this.bitWidth = button
                outputFormater()
                displayMode = true

            }
            else -> {
                error("Undefined type passed from frontend")
            }
        }
    }

    fun appendInput(symbol: Char) {
        if (this.display == ZERO_STR || displayMode || this.display == "-0") {
            this.display = "" + symbol
            displayMode = false
        } else if (symbol == '.' && display.contains(symbol)) return
        else if (display.length < DIGITNUM)
            this.display += symbol
    }

    fun clearAll() {
        this.display = ZERO_STR
        this.accumulator = 0.0
        this.displayMode = false
    }

    //Formats the number to be displayed based on selected number base and bit width
    fun outputFormater() {
        val num: Double = display.toDoubleOrNull()?:return
        if(num % 1.0 == 0.0) {
            val value = when(this.bitWidth) {
                BitWidth.DWORD -> {
                    num.toInt()
                }

                BitWidth.WORD -> {
                    num.toInt().toShort()
                }

                BitWidth.BYTE -> {
                    num.toInt().toByte()
                }
            }

            val format: String = when (this.numBase) {
                NumberBase.DEC -> NumberBase.DEC.symbol
                NumberBase.HEX -> NumberBase.HEX.symbol
                NumberBase.OCT -> NumberBase.OCT.symbol
            }
            display = String.format(format, value)

        }else if(this.numBase != NumberBase.DEC){
            errorOut()
        }
    }

    fun displayToNumber(): Double{
        return when(this.numBase){
            NumberBase.DEC -> display.toDoubleOrNull() ?: 0.0
            NumberBase.HEX -> display.toLongOrNull(16)?.toDouble()?: 0.0
            NumberBase.OCT -> display.toLongOrNull(8)?.toDouble()?: 0.0
        }
    }

    fun execute(incoming: Operation) {

        val number = if(incoming !in Operation.doesNotRequireInput) {
            displayToNumber()
        } else 0.0

        if (incoming in Operation.immediate) {
            when (incoming) {
                Operation.BACKSPACE -> display = display.dropLast(1)
                Operation.CLEAR -> {
                    clearAll(); return
                }

                Operation.CLEAR_CURRENT -> display = ZERO_STR
                Operation.CHANGE_SIGN -> display =
                    if (display[0] == '-') display.substring(1) else "-$display"

                Operation.MEMORY_ADD -> {
                    memory += number; displayMode = true
                }

                Operation.MEMORY_SUBTRACT -> {
                    memory -= number; displayMode = true
                }

                Operation.MEMORY_READ -> {
                    display = memory.toString()
                }

                Operation.MEMORY_CLEAR -> {
                    memory = 0.0
                }

                Operation.SIN -> {
                    display = fitDouble(sin(number).toString())
                }

                Operation.COS -> {
                    display = fitDouble(cos(number).toString())
                }

                Operation.TAN -> {
                    display = fitDouble(tan(number).toString())
                }

                Operation.ARCSIN -> {
                    display = fitDouble(asin(number).toString())
                }

                Operation.ARCCOS -> {
                    display = fitDouble(acos(number).toString())
                }

                Operation.ARCTAN -> {
                    display = fitDouble(atan(number).toString())
                }

                Operation.SQUARED -> {
                    display = fitDouble(number.pow(2.0).toString())
                }

                Operation.SQUARE_ROOT -> {
                    display = fitDouble(sqrt(number).toString())
                }

                Operation.LOG -> {
                    display = fitDouble(log2(number).toString())
                }

                Operation.ANTI_LOG -> {
                    display = fitDouble(10.0.pow(number).toString())
                }

                Operation.RECIPROCAL -> {
                    display = fitDouble((1.0 / number).toString())
                }

                Operation.FACTORIAL -> {
                    display = fitDouble(factorial(number.toInt()).toString())
                }

                Operation.PI -> {
                    display = PI.toString()
                }

                Operation.EULER -> {
                    display = EULER.toString()
                }

                Operation.PERCENT -> {
                    display = fitDouble((accumulator * (number / 100.0)).toString())
                }

                Operation.RNG -> {
                    display = fitDouble(Random.Default.nextDouble().toString())
                }

                Operation.NAT_LOG -> {
                    display = fitDouble(log(number, EULER).toString())
                }
                Operation.NOT -> {display = if(number % 1 == 0.0)fitDouble(number.toInt().inv().toString())else errorOut()}

                else -> error("Unreachable branch triggered in execute")
            }
        } else {
            when (this.operation) {
                Operation.ENTER -> {
                    accumulator = if(number.toLong() <= Int.MAX_VALUE) number else number.toInt().toDouble()
                }
                Operation.ADD -> accumulator += number
                Operation.SUBTRACT -> accumulator -= number
                Operation.MULTIPLY -> accumulator *= number
                Operation.DIVIDE -> accumulator /= number
                Operation.POWER -> accumulator = accumulator.pow(number)
                Operation.ROOT -> accumulator = accumulator.pow((1.0 / number))
                Operation.MODULO -> accumulator = accumulator % number
                Operation.AND -> accumulator = logicalOperation(accumulator, number,
                    LogicalOperators.AND)
                Operation.OR -> accumulator = logicalOperation(accumulator, number,
                    LogicalOperators.OR)
                Operation.XOR -> accumulator = logicalOperation(accumulator, number,
                    LogicalOperators.XOR)
                Operation.NOR -> accumulator = logicalOperation(accumulator, number,
                    LogicalOperators.NOR)
                Operation.NAND -> accumulator = logicalOperation(accumulator, number,
                    LogicalOperators.NAND)

                else -> error("Unreachable branch triggered in execute")
            }
            this.operation = incoming
            this.displayMode = true
            this.display = fitDouble(accumulator.toString())
        }
        outputFormater()
    }

    /**Returns a string that fits within the desired screen digits, or enters Error state*/
    private fun fitDouble(numStr: String): String {
        val num = numStr.toDoubleOrNull() ?: 0.0
        var str = numStr
        if (num % 1 == 0.0) {
            if (num.absoluteValue > MAXDISPLAYABLE) {
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

    fun logicalOperation(x: Double, y: Double, operator: LogicalOperators): Double{
        if(x % 1 != 0.0 || y % 1 != 0.0) return 0.0
        val a = x.toInt()
        val b = y.toInt()

        return when(operator){
            LogicalOperators.AND -> a.and(b).toDouble()
            LogicalOperators.OR ->  a.or(b).toDouble()
            LogicalOperators.XOR -> a.xor(b).toDouble()
            LogicalOperators.NOT -> 0.0
            LogicalOperators.NOR -> a.or(b).inv().toDouble()
            LogicalOperators.NAND -> a.and(b).inv().toDouble()
        }
    }

}


