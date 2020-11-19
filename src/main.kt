import java.io.File

fun readCOW(path: String): List<String> {
    val file = File(path)
    val words = file.readLines().joinToString(" ").split(' ')
    return words
}

fun getBlocks(source: List<String>): HashMap<Int, Int> {
    val stack = mutableListOf<Int>()
    val blocks = HashMap<Int, Int>()
    for ((i, char) in source.withIndex()) {
        if (char == "MOO") {
            stack.add(i)
        }
        if (char == "moo") {
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}

fun eval(source: List<String>) {
    val buffer = Array<Char>(500) {_->(0).toChar()}
    var ptr = 0
    var i = 0
    val blocks = getBlocks(source)
    while (i < source.size) {
        when (source[i]) {
            "moO" -> ptr += 1
            "mOo" -> ptr -= 1
            "MoO" -> buffer[ptr] = buffer[ptr] + 1
            "MOo" -> buffer[ptr] = buffer[ptr] - 1
            "OOM" -> print("${buffer[ptr].toInt()}")
            "Moo" -> print("${buffer[ptr]}")
            "MOO" -> {
                if (buffer[ptr] == (0).toChar()) {
                    i = blocks[i]!!
                }
            }
            "moo" -> {
                if (buffer[ptr] != (0).toChar()) {
                    i = blocks[i]!!
                }
            }
        }
        i+=1
    }
}

fun main() {
    val chars = readCOW("fib.cow")
    //println(getBlocks(chars))
    //println(chars)
    eval(chars)
}