// https://leetcode.com/problems/alien-dictionary/
class Solution {
    private val nodes = HashMap<Char, Node>()
    private val order = LinkedList<Node>()
    private val unvisited = mutableSetOf<Node>()
    private val visiting = HashSet<Node>()

    fun alienOrder(words: Array<String>): String {
        for (w in words) {
            for (c in w) {
                if (c !in nodes) {
                    val node = Node(c)
                    nodes[c] = node
                    unvisited.add(node)
                }
            }
        }

        for (i in 1 until words.size) {
            val prev = words[i - 1]
            val curr = words[i]
            if (prev == curr) continue
            for (j in 0 until minOf(prev.length, curr.length)) {
                val prevChar = prev[j]
                val currChar = curr[j]
                if (prevChar == currChar) continue
                nodes[prevChar]!!.precedes.add(nodes[currChar]!!)
                break
            }
            
        }

        while (unvisited.isNotEmpty()) {
            val rand = unvisited.first()
            if (!traverse(rand)) return ""
        }

        if (unvisited.size != 0) {
            // some nodes were never visited, so no solution possible
            return ""
        }

        val chars = CharArray(order.size)
        for (i in order.indices) {
            chars[i] = order[i].char
        }
        return String(chars)
    }

    /*
     * @ return true is traversal succeeded, false otherwise
     */
    private fun traverse(node: Node): Boolean {
        if (node in visiting) {
            return false
        }
        visiting.add(node)

        for (p in node.precedes) {
            if (p in unvisited) {
                val visited = traverse(p)
                if (!visited) return false
            }
        }
        order.addFirst(node)

        check(visiting.remove(node)) {order.map { it.char }.joinToString()}
        check(unvisited.remove(node)) {order.map { it.char }.joinToString()}
        return true
    }

    private class Node(
        val char: Char,
        val precedes: HashSet<Node> = HashSet()
    )
}
