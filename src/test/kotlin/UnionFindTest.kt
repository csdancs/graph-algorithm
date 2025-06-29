import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class UnionFindTest {

    @Test
    fun testFindAndUnion() {
        val uf = UnionFind(5)
        assertEquals(0, uf.find(0))
        assertEquals(1, uf.find(1))

        uf.union(0, 1)
        assertEquals(uf.find(0), uf.find(1))

        uf.union(1, 2)
        assertEquals(uf.find(0), uf.find(2))

        assertNotEquals(uf.find(0), uf.find(3))
    }
}
