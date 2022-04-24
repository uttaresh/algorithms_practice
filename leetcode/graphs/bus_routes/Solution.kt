// https://leetcode.com/problems/bus-routes/submissions/
class Solution {
    fun numBusesToDestination(routes: Array<IntArray>, source: Int, target: Int): Int {
        if (source == target) return 0
        val connections = HashMap<Int, LinkedList<Int>>() // k: stop id, v: all route numbers that stop here
        for ((number, route) in routes.withIndex()) {
            for (stop in route) {
                if (stop !in connections) {
                    connections[stop] = LinkedList()
                }
                connections[stop]!!.add(number)
            }
        }        
        val visited = HashSet<Int>() // tracks routes visited
        val q: Queue<Pair<Int, Int>> = LinkedList() // pair of route to dist from source
        for (routeNumber in connections[source]!!) {
            q.add(routeNumber to 1) // dist starts at 1 as you always have to take 1 bus
        }        
        while (q.isNotEmpty()) {
            val (routeNumber, dist) = q.poll()            
            for (stop in routes[routeNumber]) {
                if (stop == target) return dist
                for (routeNumber in connections[stop]!!) {
                    if (routeNumber !in visited) {
                        visited.add(routeNumber)
                        q.add(routeNumber to dist + 1)
                    }
                }
            }
            
        }
        return -1
    }
}
