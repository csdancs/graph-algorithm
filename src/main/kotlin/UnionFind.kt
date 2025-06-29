class UnionFind(n: Int) {
    val parent = IntArray(n) { it }

    fun find(x: Int): Int {
        if (parent[x] != x) parent[x] = find(parent[x])
        return parent[x]
    }

    fun union(x: Int, y: Int) {
        val xr = find(x)
        val yr = find(y)
        if (xr != yr) parent[xr] = yr
    }
}
