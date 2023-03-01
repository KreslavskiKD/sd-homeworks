package core

import com.github.h0tk3y.betterParse.combinators.or
import com.github.h0tk3y.betterParse.combinators.separatedTerms
import com.github.h0tk3y.betterParse.combinators.use
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.regexToken

interface Item
class Variable(val name: String, val value: String) : Item
class ParserCommand(val name: String, val params: List<String>) : Item
class Substitution(val envname: String) : Item

class Parser : Grammar<List<Item>>() {
    val envvar by regexToken("[ ]*[A-Za-z][A-Za-z0-9]+=[A-Za-z./0-9]+")
    val cmd by regexToken("[ ]*[A-Za-z]+([ ]+[A-Za-z./0-9]+)*")
    val subst by regexToken("\\$[A-Za-z][A-Za-z0-9]+")
    val pipe by regexToken("[ ]*\\|\\s+[ ]*")

    val substParser by subst use { Substitution(text.substring(1))}
    val variableParser by envvar use {
        val list = text.split("=")
        val name = list[0]
        val value = list[1]
        Variable(name, value)
    }
    val parserCommandParser by cmd use {
        val list = text.split(" ")
        val name = list[0]
        ParserCommand(name, list.subList(1, list.size))
    }

    override val rootParser by separatedTerms(substParser or variableParser or parserCommandParser, pipe)
}