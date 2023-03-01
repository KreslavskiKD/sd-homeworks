package core

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import kotlin.system.exitProcess

class Interpreter {
    private val environmentVariables = HashMap<String, String>()
    private val registeredCommands = HashMap<String, Runnable>()

    fun start() {
        while (true) {
            val input = readLine() ?: exitProcess(0)
            val res: List<Item> = Parser().parseToEnd(input)
            res.forEach {
                when (it) {
                    is Variable -> environmentVariables[it.name] = it.value
                    is Command -> {
                        println("Command name: ${it.name}")
                        it.params.forEach { param ->
                            println("parameter: $param")
                        }
                    }
                    is Substitution -> println("Substitution envname: ${it.envname}")
                }
            }
        }
    }
}