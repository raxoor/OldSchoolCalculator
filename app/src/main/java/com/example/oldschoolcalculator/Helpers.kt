package com.example.oldschoolcalculator

import kotlin.math.PI

object AngleUnitsConversion {
    private fun radToDeg(rad: Double):Double{
        return rad * (180.0/PI)
    }

    private fun radToGrad(rad: Double): Double{
        return rad * (200.0/PI)
    }

    private fun degToRad(deg: Double): Double{
        return deg * (PI/180.0)
    }

    private fun degToGrad(deg: Double): Double{
        return deg * (10.0/9.0)
    }

    private fun gradToDeg(grad: Double): Double{
        val result = grad * (9.0/10.0)
        return grad * (9.0/10.0)
    }

    private fun gradToRad(grad: Double): Double{
        return grad * (PI/200.0)
    }

    fun convert(num: Double, old: AngleUnits, new: AngleUnits): Double{
        return when(old){
            AngleUnits.RAD -> {
                if(new == AngleUnits.DEG) radToDeg(num)
                else radToGrad(num)
            }

            AngleUnits.DEG -> {
                if(new == AngleUnits.RAD) degToRad(num)
                else degToGrad(num)
            }

            AngleUnits.GRAD -> {
                if(new == AngleUnits.RAD) gradToRad(num)
                else gradToDeg(num)
            }
        }
    }
}

enum class AngleUnits{
    DEG,
    RAD,
    GRAD
}

fun factorial(num: Int): Int{
    if(num == 0) return 0
    var acc = 1
    for(i in num downTo 1){
        acc *= i
    }
    return acc
}
