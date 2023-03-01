package core

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import commands.Command
import commands.runCommandDefault
import kotlin.system.exitProcess

class Interpreter {
    private val environmentVariables = HashMap<String, String>()
    private val registeredCommands = HashMap<String, Command>()

    fun registerCommand(name: String, cmd: Command) {
        registeredCommands[name] = cmd
    }

    fun start() {
        var flag = false
        var buf = ""
        while (true) {
            val input: String = if (flag) {
                flag = false
                buf
            } else {
                readLine() ?: exitProcess(0)
            }
            val res: List<Item> = Parser().parseToEnd(input)
            res.forEach {
                when (it) {
                    is Variable -> environmentVariables[it.name] = it.value
                    is ParserCommand -> {
                        var newParams = it.params
                        if (buf != "") {
                            val additionalParams = buf
                            newParams = additionalParams + " " + it.params
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
                    is Substitution -> {
                        flag = true
                        buf = environmentVariables[it.envname] ?: ""
                    }
                }
            }
            if (buf != "") {
                println(buf)
            }
        }
    }
}