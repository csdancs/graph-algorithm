import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class IntervalGraphTest {

    private val i1 = Interval(1.0, 3.0, startInclusive = true, endInclusive = false)
    private val i2 = Interval(2.0, 4.0, startInclusive = true, endInclusive = true)
    private val i3 = Interval(5.0, 6.0, startInclusive = true, endInclusive = true)
    private val i4 = Interval(3.0, 5.0, startInclusive = false, endInclusive = false)

    @Test
    fun testBuildEdges() {
        val intervals = mutableListOf(i1, i2, i3, i4)
        val graph = IntervalGraph(intervals)
        val edges = graph.buildEdges()

        val expectedEdges = mutableListOf(
            Edge(0, 1),
            Edge(1, 3)
        )

        assertEquals(expectedEdges.size, edges.size)
        assertTrue(edges.any { it.u == 0 && it.v == 1 })
        assertTrue(edges.any { it.u == 1 && it.v == 3 })
    }

    @Test
    fun testLabelEdges() {
        val intervals = mutableListOf(i1, i2, i3, i4)
        val graph = IntervalGraph(intervals)
        val edges = graph.buildEdges()
        graph.labelEdges(edges)

        edges.forEach { edge ->
            assertEquals(1, edge.label)
        }
    }

    @Test
    fun testReduce() {
        val intervals = mutableListOf(
            Interval(1.0, 2.9, startInclusive = true, endInclusive = true),
            Interval(3.0, 4.0, startInclusive = true, endInclusive = false),
            Interval(4.5, 5.5, startInclusive = true, endInclusive = true),
            Interval(6.0, 7.0, startInclusive = true, endInclusive = false)
        )

        val graph = IntervalGraph(intervals)
        val reducedIntervals = graph.reduce()

        assertEquals(4, reducedIntervals.size)

        val merged = reducedIntervals.find { it.start == 1.0 }
        assertNotNull(merged)
        assertEquals(2.9, merged!!.end)
        assertTrue(merged.startInclusive)
        assertTrue(merged.endInclusive)

        assertTrue(reducedIntervals.any { it.start == 3.0 && !it.endInclusive })
        assertTrue(reducedIntervals.any { it.start == 4.5 && it.end == 5.5 && it.endInclusive })
        assertTrue(reducedIntervals.any { it.start == 6.0 && it.end == 7.0 && !it.endInclusive })
    }
}
