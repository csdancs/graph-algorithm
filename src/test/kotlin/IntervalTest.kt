import Interval.Companion.parseInterval
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class IntervalTest {

    @Test
    fun testOverlaps() {
        val i1 = Interval(1.0, 3.0, startInclusive = true, endInclusive = false)
        val i2 = Interval(2.0, 4.0, startInclusive = true, endInclusive = true)
        val i3 = Interval(3.0, 5.0, startInclusive = true, endInclusive = true)

        assertTrue(i1.overlaps(i2))
        assertFalse(i1.overlaps(i3))
    }

    @Test
    fun testMerge() {
        val i1 = Interval(1.0, 3.0, startInclusive = true, endInclusive = false)
        val i2 = Interval(2.0, 4.0, startInclusive = true, endInclusive = true)
        val merged = i1.merge(i2)
        assertEquals(1.0, merged.start)
        assertEquals(4.0, merged.end)
        assertEquals(true, merged.startInclusive)
        assertEquals(true, merged.endInclusive)
    }

    @Test
    fun testMergeThrowsIfNoOverlap() {
        val i1 = Interval(1.0, 2.0, startInclusive = true, endInclusive = true)
        val i2 = Interval(3.0, 4.0, startInclusive = true, endInclusive = true)
        assertThrows<IllegalArgumentException> {
            i1.merge(i2)
        }
    }

    @Test
    fun testToStringIntervals() {
        val i1 = Interval(Double.NEGATIVE_INFINITY, 0.0, startInclusive = true, endInclusive = true)
        val i2 = Interval(0.0, 0.0, startInclusive = true, endInclusive = true)
        val i3 = Interval(1.0, 5.0, startInclusive = false, endInclusive = true)

        assertEquals("[-infinity,0.0]", i1.toString())
        assertEquals("0.0", i2.toString())
        assertEquals("(1.0,5.0]", i3.toString())
    }

    @Test
    fun testParseInterval() {
        val i1 = parseInterval("[-infinity,0]")
        assertEquals(Double.NEGATIVE_INFINITY, i1.start)
        assertEquals(0.0, i1.end)
        assertTrue(i1.startInclusive)
        assertTrue(i1.endInclusive)

        val i2 = parseInterval("5")
        assertEquals(5.0, i2.start)
        assertEquals(5.0, i2.end)
        assertTrue(i2.startInclusive)
        assertTrue(i2.endInclusive)

        val i3 = parseInterval("(1,10)")
        assertFalse(i3.startInclusive)
        assertFalse(i3.endInclusive)
        assertEquals(1.0, i3.start)
        assertEquals(10.0, i3.end)
    }
}
