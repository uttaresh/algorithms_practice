// https://leetcode.com/submissions/detail/678084917/
class Solution {
    fun slidingPuzzle(board: Array<IntArray>): Int {
        val visited = HashSet<String>()
        val q: Queue<Pair<String, Int>> = LinkedList()        
        var startBoard = boardToString(board)
        q.add(startBoard to 0)
        while (q.isNotEmpty()) {
            val (curr, dist) = q.poll()
            if (curr == TARGET) return dist
            visited.add(curr)
            val moves = generateMoves(curr)
            for (move in moves) {
                if (move !in visited) {
                    q.add(move to dist + 1)
                }
            }
        }                
        return -1
    }
    
    fun generateMoves(config: String): List<String> {
        val zeroPos = config.indexOf('0')
        val moves = LinkedList<String>()
        if ((zeroPos % 3) > 0) {
            // swap left tile
            moves.add(swap(config, zeroPos, zeroPos - 1))
        }
        if ((zeroPos % 3) < 2) {
            // swap right tile
            moves.add(swap(config, zeroPos, zeroPos + 1))
        }
        // swap above/below tile:
        moves.add(swap(config, zeroPos, (zeroPos + 3) % 6))
        return moves
    }
    
    fun swap(s: String, zeroIdx: Int, swapIdx: Int): String {
            val moveChars = s.toCharArray()
            moveChars[zeroIdx] = moveChars[swapIdx]
            moveChars[swapIdx] = '0'
            return String(moveChars)
    }
    
    fun boardToString(board: Array<IntArray>): String {
        val chars = CharArray(6) {'0'}
        var k = 0
        for (i in 0..1) {
            for (j in 0..2) {
                chars[k] = '0' + board[i][j]
                k++
            }            
        }
        return String(chars)
    }
    
    companion object {
        const val TARGET = "123450"
    }
}
