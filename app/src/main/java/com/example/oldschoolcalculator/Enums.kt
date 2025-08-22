package com.example.oldschoolcalculator

enum class LogicalOperators{
    AND,
    OR,
    XOR,
    NOT,
    NOR,
    NAND
}

enum class NumberBase{
    BIN,
    OCT,
    DEC,
    HEX
}

enum class BitWidth{
    BYTE,
    WORD,
    DWORD,
    QWORD
}

enum class AngleUnits{
    DEG,
    RAD,
    GRAD
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
    ENTER("="),
    MEMORY_ADD("M+"),
    MEMORY_SUBTRACT("M-"),
    MEMORY_READ("MR"),
    MEMORY_CLEAR("MC"),
    HEX_A("A"),
    HEX_B("B"),
    HEX_C("C"),
    HEX_D("D"),
    HEX_E("E"),
    HEX_F("F"),
    AND("AND"),
    OR("OR"),
    XOR("XOR"),
    NOT("NOT"),
    NOR("NOR"),
    NAND("NAND"),
    SIN("sin"),
    ARCSIN("arcsin"),
    COS("cos"),
    ARCCOS("arccos"),
    TAN("tan"),
    ARCTAN("arctan"),
    SQUARED("squared"),
    POWER("power"),
    SQUARE_ROOT("square root"),
    ROOT("root"),
    LOG("log"),
    ANTI_LOG("anti log"),
    NAT_LOG("natural log"),
    TO_DEG("to deg"),
    FROM_DEG("from deg"),
    RECIPROCAL("reciprocal"),
    FACTORIAL("x!"),
    PI("pi"),
    EULER("e"),
    MODULO("mod"),
    PERCENT("%"),
    RNG("rng")
    ;

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