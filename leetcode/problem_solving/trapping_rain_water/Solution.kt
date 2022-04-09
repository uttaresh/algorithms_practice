// https://leetcode.com/problems/trapping-rain-water/
// https://leetcode.com/submissions/detail/677047198/

class Solution {
    fun trap(height: IntArray): Int {
        return trap_MostEfficient(height)
    }
    
    private fun trap_MostEfficient(height: IntArray): Int {
        // Same idea as the "more efficient" solution, except re-use
        // the same array to pre-computer the "water line" as the min
        // of the highest wall on each side. Reduces space requirements
        // to just one array. Still O(n) but great performance on
        // LeetCode. Beats 99% runtime and 97% mem of all submissions
        val n = height.size
        val waterLine = IntArray(n)
        
        // set water line based on left highest wall
        for (x in 1 until n - 1) {
            val leftHighest = maxOf(waterLine[x - 1], height[x - 1])
            waterLine[x] = leftHighest
        }
        
        // adjust water line based on min of right highest wall and left highest wall
        // also aggregate the water contribution from each x
        var volume = 0
        for (x in n - 2 downTo 1) {
            val rightHighest = maxOf(waterLine[x + 1], height[x + 1])
            waterLine[x] = minOf(waterLine[x], rightHighest)
            val diff = waterLine[x] - height[x]
            val dv = if (diff < 0) 0 else diff
            volume += dv
        }
        
        return volume
    }
    
    private fun trap_MoreEfficient(height: IntArray): Int {
        // same idea as the brute force, except we pre-compute the left and right
        // highest walls for every x, bringing the runtime down to O(n) while
        // increasing space to O(n)
        val n = height.size
        val leftHighest = IntArray(n)
        for (x in 1 until n) {
            leftHighest[x] = maxOf(leftHighest[x - 1], height[x - 1])
        }
        
        val rightHighest = IntArray(n)
        for (x in n - 2 downTo 1) {
            rightHighest[x] = maxOf(rightHighest[x + 1], height[x + 1])
        }
        
        var volume = 0
        for (x in 1 until n - 1) {
            fun zeroBound(num: Int): Int {
                return if (num < 0) 0 else num
            }
            val dv = zeroBound(minOf(leftHighest[x], rightHighest[x]) - height[x])
            volume += dv
        }
        return volume
    }
    
    private fun trap_BruteForce(height: IntArray): Int {
        val n = height.size
        var volume = 0
        for (x in 0 until n) {
            // find left highest wall
            var leftHighest = 0
            for (i in 0 until x) leftHighest = maxOf(leftHighest, height[i])
            
            // find right highest wall
            var rightHighest = 0
            for (i in x + 1 until n) rightHighest = maxOf(rightHighest, height[i])
                        
            // calculate water volume for this x. cannot be less than zero
            val dv = maxOf(0, minOf(leftHighest, rightHighest) - height[x])
            
            // increment total volume
            volume += dv
        }
        return volume
    }
}
