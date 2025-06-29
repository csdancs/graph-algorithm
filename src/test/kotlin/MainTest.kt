import Interval.Companion.parseInterval
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class MainTest {

    @Test
    fun testParseInterval_singlePoint() {
        val input = "2.0"
        val interval = parseInterval(input)
        assertEquals(2.0, interval.start)
        assertEquals(2.0, interval.end)
        assertTrue(interval.startInclusive)
        assertTrue(interval.endInclusive)
    }

    @Test
    fun testParseInterval_range() {
        val input = "[1.0,5.0)"
        val interval = parseInterval(input)
        assertEquals(1.0, interval.start)
        assertEquals(5.0, interval.end)
        assertTrue(interval.startInclusive)
        assertFalse(interval.endInclusive)
    }

    @Test
    fun testParseInterval_infinity() {
        val input = "[-infinity,infinity]"
        val interval = parseInterval(input)
        assertEquals(Double.NEGATIVE_INFINITY, interval.start)
        assertEquals(Double.POSITIVE_INFINITY, interval.end)
        assertTrue(interval.startInclusive)
        assertTrue(interval.endInclusive)
    }

    @Test
    fun testToString() {
        val i1 = Interval(1.0, 2.0, startInclusive = true, endInclusive = false)
        assertEquals("[1.0,2.0)", i1.toString())

        val i2 = Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
            startInclusive = true,
            endInclusive = true
        )
        assertEquals("[-infinity,infinity]", i2.toString())

        val i3 = Interval(5.0, 5.0, startInclusive = true, endInclusive = true)
        assertEquals("5.0", i3.toString())
    }

    @Test
    fun testMainLogicWithMockedInput() {
        val lines = listOf(
            "[1.0,3.0)",
            "[2.5,4.0]",
            "[5.0,6.0]"
        )

        val intervals = lines.map { parseInterval(it) }.toMutableList()
        val graph = IntervalGraph(intervals)
        val reduced = graph.reduce()

        assertEquals(2, reduced.size)
        assertTrue(reduced.any { it.start == 1.0 && it.end == 4.0 })
        assertTrue(reduced.any { it.start == 5.0 && it.end == 6.0 })
    }
}
