package com.example.oldschoolcalculator

enum class LogicalOperators {
    AND,
    OR,
    XOR,
    NOT,
    NOR,
    NAND
}

enum class NumberBase(val symbol: String) {
    OCT("%o"),
    DEC("%d"),
    HEX("%X")
}

enum class BitWidth {
    BYTE,
    WORD,
    DWORD
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
        val immediate = listOf(
            BACKSPACE,
            CHANGE_SIGN,
            CLEAR_CURRENT,
            CLEAR,
            MEMORY_ADD,
            MEMORY_SUBTRACT,
            MEMORY_READ,
            MEMORY_CLEAR,
            SIN,
            COS,
            TAN,
            ARCSIN,
            ARCCOS,
            ARCTAN,
            SQUARED,
            SQUARE_ROOT,
            LOG,
            ANTI_LOG,
            RECIPROCAL,
            FACTORIAL,
            PI,
            EULER,
            PERCENT,
            RNG,
            NAT_LOG,
            NOT
        )
        val doesNotRequireInput = arrayOf(
            BACKSPACE,
            CLEAR,
            CLEAR_CURRENT,
            CHANGE_SIGN,
            MEMORY_READ,
            MEMORY_CLEAR,
            PI,
            EULER,
            RNG
        )
    }
}

enum class Digit(val symbol: Char) {
    ONE('1'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    ZERO('0'),
    DELIMITER('.'),
    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    ;
}