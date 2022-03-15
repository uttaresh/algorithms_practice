class Solution {
    fun longestDiverseString(a: Int, b: Int, c: Int): String {                        
        val aStats = CharStats('a', a)
        val bStats = CharStats('b', b)
        val cStats = CharStats('c', c)
        val statsArray = arrayOf(aStats, bStats, cStats)
        
        val sb = StringBuilder()
        var consecutiveCount = 0
        var consecutiveTarget = 'z'
        
        // loop until none remain
        while (aStats.remaining != 0 || bStats.remaining != 0 || cStats.remaining != 0) {
            // sort by most remaining first
            statsArray.sortBy { -1 * it.remaining } 
            
             // if the most remaining is zero, we are done
            if (statsArray.first().remaining == 0) break
            
            // target the one with most remaining unless it was consecutively targeted twice
            var target: CharStats? = null
            for (i in 0..2) {
                if (statsArray[i].remaining > 0 && statsArray[i].char != consecutiveTarget || consecutiveCount < 2) {
                    target = statsArray[i]
                    break
                }
            }
            // if no target found, not possible to append any more
            if (target == null) break 
            
            // append the target to string, reduce its count
            sb.append(target!!.char)
            target!!.remaining--
            
            // consecutive letters rule management
            if (target!!.char == consecutiveTarget) {
                consecutiveCount++
            } else {
                consecutiveCount = 1
                consecutiveTarget = target!!.char
            }
        }
        //println(statsArray.joinToString())
        return sb.toString()
    }
    
    data class CharStats(
        val char: Char,
        var remaining: Int
    )

}
