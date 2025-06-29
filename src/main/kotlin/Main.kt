import java.io.File

fun main() {
    val inputFile = File("src/main/kotlin/resources/input1.txt")
    val outputFile = File("src/main/kotlin/outputs/output1.txt")

    val intervals = inputFile.readLines()
        .filter { it.isNotBlank() }
        .map { Interval.parseInterval(it) }
        .toMutableList()

    val graph = IntervalGraph(intervals)
    val reducedIntervals = graph.reduce()

    outputFile.printWriter().use { out ->
        reducedIntervals.forEach { out.println(it.toString()) }
    }

    println("Merged intervals written to ${outputFile.absolutePath}")
}
