// Ridiculous problem, waste of time... but this solution works efficiently
// https://leetcode.com/submissions/detail/680014614/
class Solution {
    fun fractionToDecimal(numerator: Int, denominator: Int): String {   
        if (numerator == 0) return "0"

        val sign = if ((numerator >= 0 && denominator >= 0) || (numerator < 0 && denominator < 0)) {
            ""
        } else "-"
        
        val positiveResult = positiveFractionToDecimal(Math.abs(numerator.toLong()), Math.abs(denominator.toLong()))
        
        return sign + positiveResult
    }
    
    
    fun positiveFractionToDecimal(numerator: Long, denominator: Long): String {   
        val numStr = "$numerator"
        val memo = HashMap<Long, Int>() // map of remainder to position after decimal
        var ans = StringBuilder()
        
        var i = 0
        var periodMarked = false
        var remainder = 0L
        while (remainder < denominator && i < numStr.length) {
            remainder = (remainder * 10) + (numStr[i] - '0').toInt()
            i++
        }                
        while (i < numStr.length || remainder > 0) {
            if (remainder < denominator) {     
                if (i < numStr.length) {
                    remainder = (remainder * 10) + (numStr[i] - '0').toInt()
                    i++
                } else {
                    if (!periodMarked) {
                        if (ans.isEmpty()) ans.append('0')
                        ans.append('.')
                        periodMarked = true
                    }
                    remainder = remainder * 10                    
                }
            }

            var multiple = 1
            while (multiple * denominator <= remainder) {
                multiple++
            }
            multiple--
            remainder = remainder - (multiple * denominator)
            ans.append(multiple)
            if (periodMarked) {
                if (remainder in memo) {
                    val repeatStart = memo[remainder]!!
                    ans.insert(repeatStart, '(')
                    ans.append(')')
                    break
                }
                memo[remainder] = ans.length // mark the index at which this remainder was seen
            }
        }
        return ans.toString()
    }
}
