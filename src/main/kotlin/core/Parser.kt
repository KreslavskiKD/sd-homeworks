package core

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser

interface Item
class Substitutions(val envnames: List<String>) : Item
class Variable(val name: String, val value: String) : Item
class ParserCommand(val name: String, val params: String) : Item
class VariableWithSubstitution(val name: String, val valueWithSubstitution: String) : Item
class ParserCommandWithSubstitution(val name: String, val paramsWithSubstitution: String) : Item

class Parser : Grammar<List<Item>>() {
    val envvar by regexToken("[A-Za-z][A-Za-z0-9]*[=][A-Za-z./0-9]+")
    val envvarws by regexToken("[A-Za-z][A-Za-z0-9]*[=](([A-Za-z./0-9]+)|(\\$[A-Za-z][A-Za-z0-9]*)+)")
    val cmd by regexToken("[A-Za-z]+([ ]+[A-Za-z./0-9\\-]+)*")
    val cmdws by regexToken("(([A-Za-z]+([ ]+[A-Za-z./0-9\\-]+)*)+)(([A-Za-z]+([ ]+[A-Za-z./0-9\\-]+)*)|([ ]*\\$[A-Za-z][A-Za-z0-9]*)+)*")
    val substs by regexToken("(\\$[A-Za-z][A-Za-z0-9]*)+")
    val pipe by regexToken("[ ]*\\|\\s+[ ]*")

    val variableWithSubstitutionParser by envvarws use {
        val list = text.split("=")
        val name = list[0]
        val value = list[1]
        VariableWithSubstitution(name, value)
    }

    val commandWithSubstitutionParser by cmdws use {
        val list = text.split(" ")
        val name = list[0]
        ParserCommandWithSubstitution(name, list.subList(1, list.size).joinToString(" "))
    }

    val substParser by substs use {
        Substitutions(text.split("$"))
    }

    val variableParser by envvar use {
        val list = text.split("=")
        val name = list[0]
        val value = list[1]
        Variable(name, value)
    }

    val commandParser by cmd use {
        val list = text.split(" ")
        val name = list[0]
        ParserCommand(name, list.subList(1, list.size).joinToString(" "))
    }

    override val rootParser: Parser<List<Item>> by separatedTerms(
        variableWithSubstitutionParser or commandWithSubstitutionParser or substParser or variableParser or commandParser
        , acceptZero = true
        , separator = pipe
    )
}