// https://leetcode.com/problems/pour-water/
// https://leetcode.com/submissions/detail/676763650/
class Solution {
    fun pourWater(heights: IntArray, volume: Int, k: Int): IntArray {
        val n = heights.size
        var remaining = volume
        while (remaining > 0) {
            var placed = false
            
            // check slope down on left
            var i = k
            while (i > 0 && heights[i - 1] <= heights[i]) i--
            if (heights[i] < heights[k]) {
                // if an altitude lower than where drop landed is found, then the drop will flow left
                placed = true
                val targetHeight = heights[i]
                // find the nearest point at the target altitude:
                i = k
                while (heights[i] != targetHeight) i--                
            } else {
                // check slope down on right
                i = k
                while (i < n - 1 && heights[i + 1] <= heights[i]) i++
                if (heights[i] < heights[k]) {
                    // if an altitude lower than where drop landed is found, then the drop will flow right
                    placed = true
                    val targetHeight = heights[i]
                    // find the nearest point at the target altitude:
                    i = k
                    while (heights[i] != targetHeight) i++
                } else {
                    // if no altitude found lower than where the drop landed, the drop will stay in place
                    i = k
                }
            }
         
            heights[i] = heights[i] + 1
            remaining--
            // println(heights.joinToString(",","[","]"))
        }
        return heights
    }
}
