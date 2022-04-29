// https://leetcode.com/problems/evaluate-division/submissions/
class Solution {
    private val nodes = HashMap<String, Node>()    
    
    fun calcEquation(equations: List<List<String>>, values: DoubleArray, queries: List<List<String>>): DoubleArray {
        buildGraph(equations, values)
        //printGraph()
        return queries
            .map { 
                val numerator = it.first()
                val denominator = it.last()
                if (numerator !in nodes || denominator !in nodes) {
                    -1.0
                } else {
                    val start = nodes[numerator]!!
                    val end = nodes[denominator]!!
                    val visited = HashSet<Node>()
                    getCostToNode(visited, start, end)
                }
            }
            .toDoubleArray()
    }
    
    private fun buildGraph(equations: List<List<String>>, values: DoubleArray) {        
        for (i in equations.indices) {
            val numerator = equations[i].first()
            val denominator = equations[i].last()
            val weight = values[i]
            if (numerator !in nodes.keys) {
                nodes[numerator] = Node(numerator)
            }
            if (denominator !in nodes.keys) {
                nodes[denominator] = Node(denominator)
            }
            val numeratorNode = nodes[numerator]!!
            val denominatorNode = nodes[denominator]!!
            
            numeratorNode.neighbors[denominatorNode] = weight
            denominatorNode.neighbors[numeratorNode] = 1.0 / weight
        }
    }
    
    private fun getCostToNode(visited: HashSet<Node>, start: Node, end: Node): Double {
        visited.add(start)
        if (start == end) return 1.0
        for ((n, c) in start.neighbors) {
            if (n !in visited) {                    
                val costFromNeighbor = getCostToNode(visited, n, end)
                if (costFromNeighbor != -1.0) {                        
                    return c * costFromNeighbor
                }
            }                
        }
        return -1.0
    }
    
    class Node(
        val name: String,
        val neighbors: HashMap<Node, Double> = HashMap()
    )
    
    private fun printGraph() {
        for ((name, node) in nodes) {
            print("$name | ")
            for ((neighbor, weight) in node.neighbors) {
                print("${neighbor.name}=$weight, ")
            }
            println()
        }
    }
}
