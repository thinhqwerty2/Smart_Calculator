package calculator

import java.util.*

class Input {
    companion object Instance {

        fun isValidVarName(varName: String, print: Boolean = true): Boolean {
            return if (Regex("[a-zA-z]+").matches(varName)) {
                true
            } else {
                if (print) {
                    println("Invalid identifier")
                }
                false
            }
        }

        fun isKnownVar(varName: String): Boolean {
            return if (Calculator.mapVariable.containsKey(varName)) {
                true
            } else {
                println("Unknown variable")
                false
            }
        }

        fun preProcess(s: String): String {

            var count = 0
            if (s[s.lastIndex] in "+-/*") {
                throw Exception()
            }
            for (i in s.indices) {
                if (s[i] in "+-") {
                    count++
                }
            }
            var new = s
            repeat(count) {
                new = new
                    .replace(Regex("--"), "+")
                    .replace(Regex("\\++"), "+")
                    .replace(Regex("-+"), "-")
                    .replace(Regex("\\+-"), "-")
            }
            //Thêm số 0 ở đầu biểu thức
            return if (new[0] in "+-") "0$new" else new
        }

        private fun priorityOperator(op:String):Int{
            return when(op){
                "+","-" -> 0
                "*","/" -> 1
//                "(",")" -> 2
                else -> -1
            }
        }

        fun infixToPostfix(s: String): MutableList<String> {
            val cleanInput = preProcess(s)
            val pattern = Regex("[+-/*()]|[0-9]+|[a-zA-Z]+")
            val tokens = pattern.findAll(preProcess(cleanInput)).map { it.value }.toList()
            val rs = mutableListOf<String>()
            val stackOperator=Stack<String>()
            val opArray=Regex("[+\\-*/]")
            for(token in tokens){
                if(Regex("\\d+|[a-zA-Z]+").matches(token)){
                    rs.add(token)
                    continue
                }
                if(stackOperator.empty()||stackOperator.peek()=="("){
                    stackOperator.push(token)
                    continue
                }
                if(opArray.matches(token) && priorityOperator(token)> priorityOperator(stackOperator.peek())){
                    stackOperator.push(token)
                    continue
                }
                if(opArray.matches(token) && priorityOperator(token)<= priorityOperator(stackOperator.peek()) ){
                    while (true )
                    {
                        if(stackOperator.empty()){
                            stackOperator.push(token)
                            break
                        }
                        if(priorityOperator(token)<= priorityOperator(stackOperator.peek())||stackOperator.peek()=="("){
                            rs.add(stackOperator.pop())
                        } else{
                            stackOperator.push(token)
                            break
                        }
                    }
                }

                if(token =="("){
                    stackOperator.push(token)
                    continue
                }
                if(token == ")"){
                    while (true){
                        if(stackOperator.peek()!="("){
                            rs.add(stackOperator.pop())
                        } else{
                            stackOperator.pop()
                            break
                        }

                    }
                }

            }
            while (!stackOperator.empty()){
                rs.add(stackOperator.pop())
            }
            return rs
        }
    }


    fun handleInput(s: String): List<String> {
        return infixToPostfix(preProcess(s))
    }


}