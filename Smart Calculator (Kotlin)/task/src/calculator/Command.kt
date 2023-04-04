package calculator

class Command {

    companion object {
        fun handleCommand(input: String) {
            when (input) {
                "/exit" -> {
                    Controller.Exit.noExit = false
                    exitCommand()
                }

                "/help" -> helpCommand()
                else -> unknownCommand()
            }
        }

        private fun unknownCommand() {
            println("Unknown Command")
        }

        fun helpCommand() {
            println("The program calculates the sum of numbers")
        }

        fun exitCommand() {
            println("Bye!")
        }
    }
}
