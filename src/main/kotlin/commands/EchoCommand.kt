package commands

class EchoCommand : Command {
    private var result: String = ""

    override fun run(params: String?, piped: Boolean) {
        if (params != null) {
            result = params
        }
    }

    override fun returnsResult(): Boolean = true

    override fun getResult(): String = result
}