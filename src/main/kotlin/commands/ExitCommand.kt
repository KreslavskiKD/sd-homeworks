package commands

import kotlin.system.exitProcess

class ExitCommand : Command {
    override fun run(params: String?) {
        exitProcess(0)
    }

    override fun returnsResult(): Boolean = false

    override fun getResult(): String = ""
}