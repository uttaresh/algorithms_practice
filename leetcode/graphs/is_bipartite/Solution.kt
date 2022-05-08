// https://leetcode.com/problems/is-graph-bipartite/submissions/
class Solution {
    fun isBipartite(graph: Array<IntArray>): Boolean {
        val n = graph.size
        val owners = HashMap<Int, Char>()
        for (start in graph.indices) {
            if (start in owners) continue
            val stack = Stack<Int>()
            stack.push(start)
            while(stack.isNotEmpty()) {
                val node = stack.pop()                
                val neighbors = graph[node]
                val thisOwner = if (owners[node] == 'A') 'A' else 'B'
                val thatOwner = if (thisOwner === 'A') 'B' else 'A'
                owners[node] = thisOwner
                for (neighbor in neighbors) {
                    if (owners[neighbor] == thisOwner) {
                        return false
                    }                    
                    if (neighbor !in owners.keys) {
                        owners[neighbor] = thatOwner
                        stack.push(neighbor)
                    }                    
                }                
            }            
        }
        
        return true
    }
}
