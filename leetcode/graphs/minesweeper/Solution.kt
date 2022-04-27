// https://leetcode.com/problems/minesweeper/
class Solution {
    fun updateBoard(board: Array<CharArray>, click: IntArray): Array<CharArray> {
        val r = click[0]
        val c = click[1]
        val clicked = board[r][c]
        when (clicked) {
            'M' -> return board.apply { board[r][c] = 'X' }
            'E' -> revealUntilMineAdjacent(board, r, c)            
        }
        return board
    }
    
    private fun revealUntilMineAdjacent(board: Array<CharArray>, r: Int, c: Int) {
        if (board[r][c] != 'E') return
        var cnt = 0
        for (i in -1..1) {                    
            for (j in -1..1) {
                val row = r - i
                val col = c - j
                if (row < 0 || row >= board.size || col < 0 || col >= board[0].size) continue
                if (board[row][col] == 'M') cnt++
            }                    
        }
        board[r][c] = if (cnt == 0) 'B' else '0' + cnt
        if (cnt == 0) {
            for (i in -1..1) {                    
                for (j in -1..1) {
                    val row = r - i
                    val col = c - j
                    if (row < 0 || row >= board.size || col < 0 || col >= board[0].size) continue
                    revealUntilMineAdjacent(board, row, col)
                }
            }
        }
    }
    
}
