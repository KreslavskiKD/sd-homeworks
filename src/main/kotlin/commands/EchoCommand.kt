package commands

class EchoCommand : Command {
    private var result: String = ""

    override fun run(params: List<String>) {
        result = params.joinToString(separator = " ")
    }

    override fun returnsResult(): Boolean = true

    override fun getResult(): String = result
}