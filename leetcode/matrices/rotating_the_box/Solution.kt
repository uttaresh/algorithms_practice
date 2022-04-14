// https://leetcode.com/submissions/detail/680119317/
class Solution {
    fun rotateTheBox(box: Array<CharArray>): Array<CharArray> {
        val m = box.size
        val n = box[0].size
        
        val rotated = Array<CharArray>(n) { CharArray(m) }
        
        for (i in 0 until n) {
            for (j in 0 until m) {
                rotated[i][j] = box[m - 1 - j][i]
            }
        }
        
        for (col in 0 until rotated[0].size) {
            var bottom = rotated.size - 1
            var top = bottom - 1
            while (top >= 0) {
                if (rotated[bottom][col] == '.') {
                    // if empty, find the next stone above before obstacle and ceiling and place it here
                    while (top >= 0 && rotated[top][col] == '.') top--
                    
                    if (top < 0) {
                        // we've evaluated to the ceiling. move on to next col
                        break
                    }
                    if (rotated[top][col] == '#') {
                        // drop this stone to the bottom
                        rotated[bottom][col] = '#'
                        rotated[top][col] = '.'
                        bottom--
                        top--
                    } else if (rotated[top][col] == '*') {
                        // we've hit an obstacle. reset bottom to one above
                        bottom = top - 1
                        top = bottom
                    }
                } else {
                    bottom--
                    top = bottom - 1
                }
            }
        }
        
        return rotated
    }
}
