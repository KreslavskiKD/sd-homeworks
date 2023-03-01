package commands

import java.io.File
import java.io.IOException
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

@Throws(IOException::class)
fun String.runCommand(workingDir: File): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    proc.waitFor(60, TimeUnit.MINUTES)
    val errorStream = proc.errorStream.bufferedReader().readText()
    if (errorStream.isNotEmpty()) {
        return errorStream
    }
    return proc.inputStream.bufferedReader().readText()
}

fun runCommandDefault(cmd: String): String {
    val path = Paths.get("").toAbsolutePath().toString()
    return try {
        cmd.runCommand(File(path))
    } catch (e: IOException) {
        println(e.message)
        ""
    }
}