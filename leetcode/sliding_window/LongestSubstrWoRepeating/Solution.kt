// https://leetcode.com/problems/longest-substring-without-repeating-characters/submissions/
class Solution {
    fun lengthOfLongestSubstring(s: String): Int {
        var i = 0
        var j = 0
        var longest = 0
        var seen = HashSet<Char>()
        while (j < s.length) {
            if (s[j] in seen) {     
                // remove until the previous char that's dup is removed
                while (s[i] != s[j]) {
                    seen.remove(s[i])
                    i++
                }
                i++
            } else {                
                seen.add(s[j])
                if (seen.size > longest) longest = seen.size
            }
            j++
        }
        return longest
    }
}
