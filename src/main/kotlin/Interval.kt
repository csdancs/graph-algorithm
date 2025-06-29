data class Interval(
    val start: Double,
    val end: Double,
    val startInclusive: Boolean,
    val endInclusive: Boolean
) {
    /**
     * Két csúcs akkor lesz összekötve, ha a metszetük nem üres.
     * A metszet lehet egyetlen pont is.
     */
    fun overlaps(other: Interval): Boolean {
        if (end < other.start || other.end < start) return false
        if (end == other.start)
            return endInclusive && other.startInclusive
        if (start == other.end)
            return startInclusive && other.endInclusive
        return true
    }

    /**
     * partially here:
     * Algoritmus: a cél az élek eliminálása oly módon, hogy (1) a csúcsokat összevonjuk, amibe a metszet halmazrendszer kerül.
     * (2) a lehető legkisebb elemszámú csúcs legyen a végeredmény.
     */
    fun merge(other: Interval): Interval {
        if (!overlaps(other)) {
            throw IllegalArgumentException("Intervals don't overlap")
        }

        val newStart = minOf(start, other.start)
        val newEnd = maxOf(end, other.end)

        val newStartInclusive = when (newStart) {
            this.start -> this.startInclusive
            other.start -> other.startInclusive
            else -> true
        }

        val newEndInclusive = when (newEnd) {
            this.end -> this.endInclusive
            other.end -> other.endInclusive
            else -> true
        }
        return Interval(newStart, newEnd, newStartInclusive, newEndInclusive)
    }

    override fun toString(): String {
        val startStr = if (start == Double.NEGATIVE_INFINITY) "-infinity" else start.toString()
        val endStr = if (end == Double.POSITIVE_INFINITY) "infinity" else end.toString()
        val open = if (startInclusive) "[" else "("
        val close = if (endInclusive) "]" else ")"
        return if (start == end && startInclusive && endInclusive) startStr
        else "$open$startStr,$endStr$close"
    }

    companion object {
        @JvmStatic
        fun parseInterval(line: String): Interval {
            val trimmed = line.trim()
            if (!trimmed.contains(",")) {
                val point = if (trimmed == "-infinity") Double.NEGATIVE_INFINITY else trimmed.toDouble()
                return Interval(start = point, end = point, startInclusive = true, endInclusive = true)
            }

            val startInclusive = trimmed.startsWith("[")
            val endInclusive = trimmed.endsWith("]")

            val content = trimmed.drop(1).dropLast(1)
            val (startStr, endStr) = content.split(",")

            val start = when (startStr.trim()) {
                "-infinity" -> Double.NEGATIVE_INFINITY
                else -> startStr.trim().toDouble()
            }

            val end = when (endStr.trim()) {
                "infinity" -> Double.POSITIVE_INFINITY
                else -> endStr.trim().toDouble()
            }

            return Interval(start, end, startInclusive, endInclusive)
        }
    }
}
