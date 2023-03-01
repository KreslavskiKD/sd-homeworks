package core

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import commands.Command
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
                        println("Command name: ${it.name}")
                        it.params.forEach { param ->
                            println("parameter: $param")
                        }
                        var newParams = it.params
                        if (buf != "") {
                            val additionalParams = buf.split(" ")
                            newParams = additionalParams + it.params
                        }
                        if (registeredCommands.containsKey(it.name)) {
                            val command = registeredCommands[it.name] ?: exitProcess(-1)
                            command.run(newParams)
                            if (command.returnsResult()) {
                                buf = command.result
                            }
                        }
                    }
                    is Substitution -> println("Substitution envname: ${it.envname}")
                }
                if (buf != "") {
                    println(buf)
                }
            }
        }
    }
}