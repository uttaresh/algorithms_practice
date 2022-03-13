class Solution {
    fun reachingPoints(sx: Int, sy: Int, tx: Int, ty: Int): Boolean {
        var x = tx
        var y = ty
        while (x > sx || y > sy) {
            if (x == 0 || y == 0) break // 0's not allowed by problem statement
            if (x == sx) return (y - sy) % sx == 0 // optimize for sx reached
            else if (y == sy) return (x - sx) % sy == 0 // optimize for sy reached
            else if (x > y) { // parent is of x
                x = x - y
            } else { // parent is of y
                y = y - x
            }
        }
        return x == sx && y == sy
    }        
}
