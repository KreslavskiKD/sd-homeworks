import commands.CatCommand
import commands.EchoCommand
import commands.ExitCommand
import commands.PwdCommand
import commands.WcCommand
import core.*

/**
 * Reads line by line and prints results for each line
 */
fun main() {
    val interpreter = Interpreter()

    interpreter.registerCommand("echo", EchoCommand())
    interpreter.registerCommand("exit", ExitCommand())
    interpreter.registerCommand("pwd", PwdCommand())
    interpreter.registerCommand("cat", CatCommand())
    interpreter.registerCommand("wc", WcCommand())

    interpreter.start()
}