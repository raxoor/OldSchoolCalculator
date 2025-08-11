package com.example.oldschoolcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Calculator {
    private val zero = "0"
    private var displayMode = false
    var input by mutableStateOf( zero)
        private set
    private var accumulator: Double = 0.0
    private var operation = Operation.ENTER
        set(value) {
            execute()
            field = value
        }

    fun buttonPress(text: String){
        if(text.length == 1 && (text[0].isDigit() || text[0] == '.')) appendInput(text[0])
        else this.operation = Operation.fromSymbol(text)
    }
    fun appendInput(symbol: Char){
        if (this.input == zero || displayMode ) {this.input = "" + symbol; displayMode = false}
        else if (input.length < 10) this.input += symbol
    }

    fun clearAll(){
        this.input = zero
        this.accumulator = 0.0
    }

    fun transferToAccumulator(){
        accumulator = input.toDouble()
        input = "0"
    }

    fun execute(){
        when(this.operation){
            Operation.ENTER -> accumulator = input.toDouble()
            Operation.ADD -> accumulator += input.toDouble()
            Operation.SUBTRACT -> accumulator -= input.toDouble()
            Operation.MULTIPLY -> accumulator *= input.toDouble()
            Operation.DIVIDE -> accumulator /= input.toDouble()
        }
    }

}

enum class Operation(val symbol: String){
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
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