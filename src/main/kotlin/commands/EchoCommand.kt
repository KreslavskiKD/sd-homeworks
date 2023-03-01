package commands

class EchoCommand : Command {
    private var result: String = ""

    override fun run(params: String) {
        result = params
    }

    override fun returnsResult(): Boolean = true

    override fun getResult(): String = result
}