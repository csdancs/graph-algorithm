class IntervalGraph(private val intervals: MutableList<Interval>) {
    private val n = intervals.size
    private val edges = mutableListOf<Edge>()

    /**
     * partially here:
     * Az így felcimkézett gráfon csináljunk egy minimális feszítőfát,
     * (Kruskal) majd mindig a fa legkisebb cimkéjű élei sorrendjében keresztül hajtsuk végre az összevonásokat.
     * Persze a fából könnyen erdő lehet...
     */
    fun buildEdges(): MutableList<Edge> {
        for (i in 0 until n) {
            for (j in i + 1 until n) {
                if (intervals[i].overlaps(intervals[j])) {
                    edges.add(Edge(i, j))
                }
            }
        }
        return edges
    }

    /**
     * minden élre tegyünk egy cimkét, amely leírja, hogyha az adott élt elimináljuk, az eredeti élszám és a
     * redukált gráf élszáma különbség mekkora. Nyilván az a jó, minél kisebb ez az érték,
     * hiszen annál több lehetőség maradhat a későbbi redukcióra.
     *
     * partially here:
     * Az így felcimkézett gráfon csináljunk egy minimális feszítőfát,
     * (Kruskal) majd mindig a fa legkisebb cimkéjű élei sorrendjében keresztül hajtsuk végre az összevonásokat.
     * Persze a fából könnyen erdő lehet...
     */

    fun labelEdges(edges: MutableList<Edge>) {
        val adjacency = Array(n) { mutableSetOf<Int>() }
        edges.forEach {
            adjacency[it.u].add(it.v)
            adjacency[it.v].add(it.u)
        }
        for (edge in edges) {
            val neighborsU = adjacency[edge.u]
            val neighborsV = adjacency[edge.v]
            val common = neighborsU.intersect(neighborsV).size
            edge.label = (neighborsU.size + neighborsV.size - common - 2)
        }
    }

    fun reduce(): Set<Interval> {
        val edges: MutableList<Edge> = buildEdges()
        labelEdges(edges)
        edges.sortBy { it.label }

        val uf = UnionFind(n)

        for (edge in edges) {
            val rootU = uf.find(edge.u)
            val rootV = uf.find(edge.v)
            if (rootU != rootV) {
                val merged = intervals[rootU].merge(intervals[rootV])
                intervals[rootU] = merged
                intervals[rootV] = merged
                uf.union(rootU, rootV)
            }
        }

        val result = mutableMapOf<Int, Interval>()
        for (i in intervals.indices) {
            val root = uf.find(i)
            result[root] = intervals[root]
        }

        return result.values.toSet()
    }
}
