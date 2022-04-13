// https://leetcode.com/problems/shortest-path-to-get-all-keys/submissions/
class Solution {
    fun shortestPathAllKeys(grid: Array<String>): Int {
        val m = grid.size
        val n = grid[0].length
        lateinit var start: State
        var numLocks = 0
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] == '@') {
                    start = State(i, j, 0, 0, 0)
                }
                if (grid[i][j] in 'A'..'Z') numLocks++
            }
        }

        val tracker = VisitTracker()
        tracker.markVisited(0, start.y, start.x)

        val q: Queue<State> = LinkedList()
        q.add(start)
        while (q.isNotEmpty()) {
            val curr = q.poll()
            val cell = grid[curr.y][curr.x]

            // invalid moves: 1) we're on a wall, or 2) we haven't picked up this lock's key yet
            val isLock = cell in 'A'..'Z'
            val isRestricted = isLock && !hasKey(cell.toLowerCase(), curr.keychain)
            if (isRestricted) continue
            var keychain = curr.keychain
            var numKeys = curr.numKeys

            // pick up new keys
            if (cell in 'a'..'z' && !hasKey(cell, curr.keychain)) {
                keychain = addKey(cell, curr.keychain)
                numKeys++
            }

            // end condition
            if (numKeys == numLocks) {
                return curr.moves
            }

            // keep going
            val moves = getPossibleMoves(curr, m, n)
            for ((y, x) in moves) {
                if (grid[y][x] != '#') { // don't walk into walls
                    if (!tracker.wasVisited(keychain, y, x)) {
                        tracker.markVisited(keychain, y, x)
                        q.add(State(y, x, curr.moves + 1, keychain, numKeys))
                    }
                }
            }
        }
        return -1
    }

    private fun getPossibleMoves(c: State, m: Int, n: Int): List<Pair<Int, Int>> {
        val ret = LinkedList<Pair<Int, Int>>()
        if (c.y > 0) ret.add(c.y - 1 to c.x)
        if (c.x > 0) ret.add(c.y to c.x - 1)
        if (c.y < m - 1) ret.add(c.y + 1 to c.x)
        if (c.x < n - 1) ret.add(c.y to c.x + 1)
        return ret
    }

    /**
     * Tracks if we have visited a cell with a certain configuration of keys
     */
    private class VisitTracker {
        // map key: bit mask of possessed keys
        // map value: set of visited coordinates with possessed keys
        private val visited = HashSet<String>()

        fun wasVisited(keychain: Int, y: Int, x: Int): Boolean {
            return serialize(keychain, y, x) in visited
        }

        fun markVisited(keychain: Int, y: Int, x: Int) {
            visited.add(serialize(keychain, y, x))
        }

        private fun serialize(keychain: Int, y: Int, x: Int) = "$keychain:$y:$x"
    }

    private data class State(
        val y: Int,
        val x: Int,
        val moves: Int,
        val keychain: Int,
        val numKeys: Int
    )

    companion object {
        fun hasKey(char: Char, mask: Int): Boolean {
            val keyBit = 1 shl (char - 'a')
            return (mask and keyBit) > 0
        }

        fun addKey(char: Char, mask: Int): Int {
            val keyBit = 1 shl (char - 'a')
            return mask or keyBit
        }
    }
}
