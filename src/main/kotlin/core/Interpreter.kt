package core

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import com.github.h0tk3y.betterParse.parser.ParseException
import commands.Command
import commands.runCommandDefault
import kotlin.system.exitProcess

class Interpreter {
    private val environmentVariables = HashMap<String, String>()
    private val registeredCommands = HashMap<String, Command>()

    fun registerCommand(name: String, cmd: Command) {
        registeredCommands[name] = cmd
    }

    private fun applySubstitutions(command: String): MutableList<String> {
        val list = command.split("$").toMutableList()
        for (i in 0 until list.size) {
            if (list[i].startsWith("#")) {
                val envname = list[i].substring(1)
                list[i] = environmentVariables[envname]
                    ?: throw InterpreterException("No such variable $envname in environment")
            }
        }
        return list
    }

    fun start() {
        var flag = false
        var buf = ""
        while (true) {
            var input: String
            if (flag) {
                flag = false
                input = buf
            } else {
                input = readLine() ?: exitProcess(0)
            }
            buf = ""
            try {
                val res: List<Item> = Parser().parseToEnd(input.trim())
                var operationCount = 0
                res.forEach {
                    operationCount++
                    when (it) {
                        is VariableWithSubstitution -> {
                            val newValueWithSubstitution = it.valueWithSubstitution.replace("$", "$#")
                            environmentVariables[it.name] = applySubstitutions(newValueWithSubstitution).joinToString("")
                        }
                        is ParserCommandWithSubstitution -> {
                            var newParams = it.paramsWithSubstitution
                            if (buf != "") {
                                val additionalParams = buf
                                newParams = additionalParams + " " + it.paramsWithSubstitution
                            }
                            newParams = newParams.replace("$", "$#")
                            newParams = applySubstitutions(newParams).joinToString("")

                            if (registeredCommands.containsKey(it.name)) {
                                val command = registeredCommands[it.name]
                                if (command == null) {
                                    val cmd = it.name + " " + newParams
                                    buf = runCommandDefault(cmd)
                                } else {
                                    command.run(newParams, operationCount > 1)
                                    if (command.returnsResult()) {
                                        buf = command.result
                                    }
                                }
                            } else {
                                val cmd = it.name + " " + newParams
                                buf = runCommandDefault(cmd)
                            }
                        }
                        is Substitutions -> {
                            flag = true
                            it.envnames.forEach { name ->
                                if (name != "") {
                                    buf += environmentVariables[name]
                                        ?: throw InterpreterException("No such variable $name in environment")
                                }
                            }
                        }
                    }
                }
                if (buf != "") {
                    println(buf)
                }
            } catch (e: ParseException) {
                println(e.message)
            } catch (e: InterpreterException) {
                println(e.message)
            }
        }
    }
}

class InterpreterException(msg: String) : Exception(msg)