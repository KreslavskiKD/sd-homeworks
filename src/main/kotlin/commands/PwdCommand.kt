package commands

import java.nio.file.Paths

class PwdCommand : Command {
    private var result = ""

    override fun run(params: String?, piped: Boolean) {
        result = Paths.get("").toAbsolutePath().toString()
    }

    override fun returnsResult(): Boolean = true

    override fun getResult(): String = result
}