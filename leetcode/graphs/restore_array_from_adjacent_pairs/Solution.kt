// https://leetcode.com/problems/restore-the-array-from-adjacent-pairs/
class Solution {
    private val visited = HashSet<Node>()
    
    fun restoreArray(adjacentPairs: Array<IntArray>): IntArray {
        val nodes = HashMap<Int, Node>()
        for (pair in adjacentPairs) {
            val first = pair[0]
            val second = pair[1]
            if (first !in nodes) nodes[first] = Node(first)
            if (second !in nodes) nodes[second] = Node(second)
            nodes[first]!!.neighbors.add(nodes[second]!!)
            nodes[second]!!.neighbors.add(nodes[first]!!)
        }
        
        var start: Node? = null
        for ((num, node) in nodes) {
            if (node.neighbors.size == 1) {
                start = node
                break
            }
        }
        return restoreArray(start!!).toIntArray()     
    }
    
    // return restored array
    fun restoreArray(start: Node): LinkedList<Int> {
        visited.add(start)
        val unvisitedNeighbor = start.neighbors.filter { it !in visited }
        val remaining = if (unvisitedNeighbor.isEmpty()) {
            LinkedList<Int>()
        } else {
            restoreArray(unvisitedNeighbor.first()!!)            
        }
        remaining.add(0, start.value)
        return remaining
    }
    
    
    
    class Node(
        val value: Int,
        val neighbors: LinkedList<Node> = LinkedList()
    )
}
