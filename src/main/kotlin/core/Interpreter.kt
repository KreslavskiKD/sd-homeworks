package core

import com.github.h0tk3y.betterParse.grammar.parseToEnd

class Interpreter {

    fun start() {
        while (true) {
            val input = readLine() ?: break
            val res: List<Item> = Parser().parseToEnd(input)
            res.forEach {
                when (it) {
                    is Variable -> println("Variable name: ${it.name}, value: ${it.value}")
                    is Command -> println("Command name: ${it.name}")
                    is Substitution -> println("Substitution envname: ${it.envname}")
                }
            }
        }
    }
}