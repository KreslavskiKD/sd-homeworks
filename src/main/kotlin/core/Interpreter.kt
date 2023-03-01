package core

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import commands.Command
import commands.runCommand
import commands.runCommandDefault
import java.io.File
import java.io.IOException
import java.nio.file.Paths
import kotlin.system.exitProcess

class Interpreter {
    private val environmentVariables = HashMap<String, String>()
    private val registeredCommands = HashMap<String, Command>()

    fun registerCommand(name: String, cmd: Command) {
        registeredCommands[name] = cmd
    }

    fun start() {
        while (true) {
            val input = readLine() ?: exitProcess(0)
            val res: List<Item> = Parser().parseToEnd(input)
            var buf = ""
            res.forEach {
                when (it) {
                    is Variable -> environmentVariables[it.name] = it.value
                    is ParserCommand -> {
                        var newParams = it.params
                        if (buf != "") {
                            val additionalParams = buf
                            newParams = additionalParams + " "+ it.params
                        }
                        if (registeredCommands.containsKey(it.name)) {
                            val command = registeredCommands[it.name]
                            if (command == null) {
                                val cmd = it.name + " " + it.params
                                buf = runCommandDefault(cmd)
                            } else {
                                command.run(newParams)
                                if (command.returnsResult()) {
                                    buf = command.result
                                }
                            }
                        } else {
                            val cmd = it.name + " " + it.params
                            buf = runCommandDefault(cmd)
                        }
                    }
                    is Substitution -> println("Substitution envname: ${it.envname}")
                }
            }
            if (buf != "") {
                println(buf)
            }
        }
    }
}