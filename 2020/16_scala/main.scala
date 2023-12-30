import scala.io.Source

@main def main(): Unit = {
    val (rules, ticket, tickets) = parseData()

    val value1: Long = part1()
    val value2: Long = part2()

    println(s"part1: $value1, part2: $value2")
}

def parseData(): Array[String] = {
    val lines = Source.fromFile("input.txt").getLines.toArray

    // rules could be a map: rule -> [(low1, high1), (low2, high2), ...]
    // ticket could be an array of integers
    // tickets could be an array of arrays of integers


    return rules, ticket, tickets
}

def part1(): Long = {
    return 0
}

def part2(): Long = {
    return 0
}