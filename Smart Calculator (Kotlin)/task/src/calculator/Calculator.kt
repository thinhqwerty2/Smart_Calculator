package calculator

import java.util.*

object Calculator {

    val mapVariable = mutableMapOf<String, Int>()


    private fun printVar(input: String) {
        println("${mapVariable[input]}")
    }

    fun handleExpression(input: String): Int? {
        val postFix=Input.infixToPostfix(input)
        val rs=Stack<Int>()

        for (exp in postFix){
            if(Regex("\\d+").matches(exp)){
                rs.push(exp.toInt())
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
                    "/" -> rs.push((1.0/rs.pop()*rs.pop()).toInt())
                }
            }

        }
        println(rs.peek())
        return rs.pop()

//        for (i in tokens.indices) {
//            when (tokens[i]) {
//                "+","-" -> {
//                    operator.push(tokens[i])
//                }
//
//                else -> {
//                    number.push(
//                        try {
//                            tokens[i].toInt()
//                        } catch (e: Exception) {
//                            mapVariable[tokens[i]]
//                        }
//                    )
//
//                }
//            }
//            if (number.size == 2) {
//                val second = number.pop()
//                val first = number.pop()
//                number.push(
//                    when (operator.pop()) {
//                        "+" -> second + first
//                        "-" -> first - second
//                        else -> 0
//                    }
//                )
//            }
//
//        }
//        println(number.peek())
//        return number.pop()
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
            temp[1].toInt()
        } catch (_: Exception) {
            if (!Input.isValidVarName(temp[1], print = false)) {
                println("Invalid assignment")
                return
            }
            if (!Input.isKnownVar(temp[1])) {
                return
            }
            mapVariable[temp[1]] ?: 0
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


