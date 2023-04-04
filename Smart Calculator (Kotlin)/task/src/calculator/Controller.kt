package calculator

import java.util.*

class Controller {
    object Exit{
    var noExit=true

    }

    fun start() {
        val sc = Scanner(System.`in`)
        while (Exit.noExit) {
            val input = sc.nextLine().replace("\\s".toRegex(),"")
            when {
                Regex("/.*").matches(input) -> Command.handleCommand(input)
                input == "" -> continue
                Regex(".*=.*").matches(input) -> Calculator.handleVarAssign(input)
                Regex("\\w+$").matches(input) -> Calculator.handelVarPrint(input)
                else -> Calculator.handleExpression(input)

            }
        }
    }


}