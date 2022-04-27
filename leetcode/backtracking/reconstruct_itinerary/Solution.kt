// https://leetcode.com/problems/reconstruct-itinerary/ 

class Solution {
    private val tickets = HashMap<String, MutableList<String>>()
    private var bestItinerary: List<String>? = null
    
    fun findItinerary(tickets: List<List<String>>): List<String> {
        for (ticket in tickets) {
            val src = ticket[0]
            val dest = ticket[1]
            if (src !in this.tickets) this.tickets[src] = LinkedList()
            this.tickets[src]!!.add(dest)
        }
        
        for (destinations in this.tickets.values) {
            destinations.sort()
        }
                
        val trip = LinkedList<String>()
        trip.add("JFK")
        findItineraries(trip)
        return bestItinerary!!
    }
    
    private fun findItineraries(tripSoFar: LinkedList<String>): Boolean {                
        val curr = tripSoFar.last()
        val destinations = tickets[curr]
        if (destinations == null) {
            // not possible to go anywhere from here. did we use each ticket?
            if (tickets.isNotEmpty()) {
                // we didn't use every ticket, invalid path
                return false
            } else {
                bestItinerary = tripSoFar.toList()
                return true
            }
        }
        
        // try going to each of the destinations from here
        for (i in 0 until destinations!!.size) {
            val dest = destinations.removeAt(i)
            if (destinations.isEmpty()) tickets.remove(curr)
            
            tripSoFar.add(dest)
            val worked = findItineraries(tripSoFar)
            if (worked) return true
            
            //backtrack
            tripSoFar.removeLast()
            if (destinations.isEmpty()) tickets[curr] = destinations
            destinations.add(i, dest)
        }
        return false
    }
}
