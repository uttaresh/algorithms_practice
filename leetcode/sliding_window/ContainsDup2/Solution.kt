https://leetcode.com/problems/contains-duplicate-ii/
class Solution {
    fun containsNearbyDuplicate(nums: IntArray, k: Int): Boolean {
        val window = HashSet<Int>()
        var i = 0
        while (i < nums.size) {
            if (nums[i] in window) return true
            window.add(nums[i])
            if (window.size > k) {
                window.remove(nums[i-k])
            }
            i++
        }
        return false
    }
}
