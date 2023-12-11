import java.io.File

fun main() {
    val slopes = listOf(
        Pair(1, 1),
        Pair(3, 1),
        Pair(5, 1),
        Pair(7, 1),
        Pair(1, 2)
    )
    val counts = IntArray(5)
    val positions = IntArray(5)

    var line_index = 0
    try {
        val file = File("input.txt")
        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                for ((i, slope) in slopes.withIndex()) {
                    val (right, down) = slope
                    if (line_index % down == 0) {
                        if (line[positions[i] % line.length] == '#') {
                            counts[i] += 1
                        }
                        positions[i] += right
                    }
                }
                line_index += 1
            }
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }

    val part1 = counts[1]
    var part2 = 1
    for (element in counts) {
        part2 *= element
    }
    println("part1: ${part1}, part2: ${part2}")
}