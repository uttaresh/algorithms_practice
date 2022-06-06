// https://leetcode.com/submissions/detail/715381471/
class Solution {
    private lateinit var board: Array<CharArray>
    private val emptyCells = HashSet<Pair<Int, Int>>()
    private var count = 0
    
    fun solveSudoku(board: Array<CharArray>): Unit {
        this.board = board
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (board[i][j] == '.') {
                    emptyCells.add(i to j)
                }
            }
        }
        solve()
    }
    
    private fun solve(): Boolean {
        val targetCell = findNextEmptyCell()
        if (targetCell == null) {
            // no empty cells, game has already been solved
            return true
        }
        
        val (targetRow, targetCol) = targetCell
        
        val possibilities = findPossibilities(targetRow, targetCol)
        
        // if no possibilities, solution not possible. backtrack
        if (possibilities.isEmpty()) {
            emptyCells.add(targetRow to targetCol)
            return false
        }
        
        // try each possibility
        for (possibility in possibilities) {
            board[targetRow][targetCol] = possibility
            if (solve()) return true           
        }
                
        // unsolvable since we tried all possibilities unsuccessfully. backtrack and return
        board[targetRow][targetCol] = '.'
        emptyCells.add(targetRow to targetCol)
        return false        
    }
    
    private fun findPossibilities(targetRow: Int, targetCol: Int): Set<Char> {
        val possibilities = ('1'..'9').toMutableSet()
        
        // check row
        for (i in 0 until 9) {
            val curr = board[targetRow][i]
            if (curr != '.') {
                possibilities.remove(curr)
            }
        }
        
        // check col
        for (i in 0 until 9) {
            val curr = board[i][targetCol]
            if (curr != '.') {
                possibilities.remove(curr)
            }
        }
        
        // check square
        val squareStartRow = (targetRow / 3) * 3
        val squareStartCol = (targetCol / 3) * 3
        for (i in squareStartRow until (squareStartRow + 3)) {
            for (j in squareStartCol until (squareStartCol + 3)) {
                val curr = board[i][j]
                if (curr != '.') {
                    possibilities.remove(curr)
                }
            }
        }
        return possibilities
    }
    
    private fun findNextEmptyCell(): Pair<Int, Int>? {
        if (emptyCells.size == 0) return null
        
        var minPossibilities = Int.MAX_VALUE
        var bestCell: Pair<Int, Int>? = null
        for ((targetRow, targetCol) in emptyCells) {
            val possibilities = findPossibilities(targetRow, targetCol)
            if (possibilities.size < minPossibilities) {
                minPossibilities = possibilities.size
                bestCell = targetRow to targetCol
            }
        }        
        
        if (bestCell == null) return null
        emptyCells.remove(bestCell)
        return bestCell
    }
}
