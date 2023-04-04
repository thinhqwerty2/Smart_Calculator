package calculator

import java.math.BigInteger
import java.util.*

object Calculator {

    val mapVariable = mutableMapOf<String, BigInteger>()


    private fun printVar(input: String) {
        println("${mapVariable[input]}")
    }

    fun handleExpression(input: String): BigInteger? {
        if (!Input.isValidExpression(input)){
            println("Invalid expression")
            return 0.toBigInteger()
        }
        val postFix=Input.infixToPostfix(input)
        val rs=Stack<BigInteger>()

        for (exp in postFix){
            if(Regex("\\d+").matches(exp)){
                rs.push(exp.toBigInteger())
                continue
            }
            if(Regex("^[a-zA-Z]+$").matches(exp)){
                rs.push(mapVariable[exp])
            }
            if(Regex("^[+\\-*/]$").matches(exp)){
                when(exp){
                    "+" -> rs.push(rs.pop()+rs.pop())
                    "-" -> rs.push(-rs.pop()+rs.pop())
                    "*" -> rs.push(rs.pop()*rs.pop())
                    "/" -> rs.push((1.0/rs.pop().toDouble()*rs.pop().toDouble()).toBigDecimal().toBigInteger())
                }
            }

        }
        println(rs.peek())
        return rs.pop()


    }

    fun handleVarAssign(input: String) {
        //temp is expression of two side "="
        val temp = input.split("=").toList()

        if (!Input.isValidVarName(temp[0])) {
            return
        }
        if (temp.size > 2) {
            println("Invalid assignment")
            return
        }

        val value = try {
            temp[1].toBigInteger()
        } catch (_: Exception) {
            if (!Input.isValidVarName(temp[1], print = false)) {
                println("Invalid assignment")
                return
            }
            if (!Input.isKnownVar(temp[1])) {
                return
            }
            mapVariable[temp[1]] ?: 0.toBigInteger()
        }

        mapVariable[temp[0]] = value


    }

    fun handelVarPrint(varName: String) {
        if (!Input.isValidVarName(varName)) {
            return
        }
        if (Input.isKnownVar(varName)) {
            printVar(varName)
        }
    }

}


