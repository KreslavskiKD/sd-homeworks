import commands.EchoCommand
import commands.ExitCommand
import core.*

/**
 * Reads line by line and prints results for each line
 */
fun main(args: Array<String>) {
    val interpreter = Interpreter()

    interpreter.registerCommand("echo", EchoCommand())
    interpreter.registerCommand("exit", ExitCommand())

    interpreter.start()
}