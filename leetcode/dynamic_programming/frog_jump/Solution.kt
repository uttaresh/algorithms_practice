class Solution {
    fun canCross(stones: IntArray): Boolean {
        val state = HashMap<Int, HashSet<Int>>()
        for (stone in stones) {
            state[stone] = HashSet<Int>()
        }
        state[0]!!.add(0) // first jump was required to be 1, from stone 0 to stone 1. So k @ stone 0 = 0
        for (stone in stones) {
            val prevJumps = state[stone]!!
            for (k in prevJumps) {
                for (nextJump in k-1..k+1) {
                    if (nextJump > 0 && stone + nextJump in state) {
                        state[stone + nextJump]!!.add(nextJump)
                    }
                }                
            }
        }
        return state[stones.last()]!!.isNotEmpty()
    }
}
