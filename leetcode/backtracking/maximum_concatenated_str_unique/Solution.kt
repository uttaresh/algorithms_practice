// https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
class Solution {
    fun maxLength(arr: List<String>): Int {
        return findMaxLengthByBacktracking(arr, -1, "")
    }
    
    fun findMaxLengthByBacktracking(arr: List<String>, start: Int, base: String): Int {
        // skip if base so far has duplicates
        if (base.toSet().size < base.length) return -1        
        
        var maxLength = base.length
        for (i in start + 1 until arr.size) {            
            maxLength = maxOf(maxLength, findMaxLengthByBacktracking(arr, i, base + arr[i]))
        }
        return maxLength
    }    
}
