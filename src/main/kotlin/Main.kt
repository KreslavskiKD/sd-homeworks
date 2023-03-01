import commands.EchoCommand
import commands.ExitCommand
import commands.PwdCommand
import core.*

/**
 * Reads line by line and prints results for each line
 */
fun main(args: Array<String>) {
    val interpreter = Interpreter()

    interpreter.registerCommand("echo", EchoCommand())
    interpreter.registerCommand("exit", ExitCommand())
    interpreter.registerCommand("pwd", PwdCommand())

    interpreter.start()
}