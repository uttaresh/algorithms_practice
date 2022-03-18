class Solution {
    fun numIslands(grid: Array<CharArray>): Int {
        val m = grid.size
        val n = grid[0].size
        val visited = Array<BooleanArray>(m) {
            BooleanArray(n) {false}
        }
        var islandCount = 0
        val s = Stack<Pair<Int, Int>>()
        var row = 0
        var col = 0
        while (row < m) {
            col = 0
            while (col < n) {
                if (visited[row][col] || grid[row][col] == '0') {
                    col++
                    continue
                }
                islandCount++
                s.push(row to col)
                while (s.isNotEmpty()) {
                    val (currRow, currCol) = s.pop()
                    if (visited[currRow][currCol] == false && grid[currRow][currCol] == '1') { 
                        visited[currRow][currCol] = true
                        if (currRow > 0) s.push(currRow - 1 to currCol)
                        if (currRow < m - 1) s.push(currRow + 1 to currCol)
                        if (currCol > 0) s.push(currRow to currCol - 1)
                        if (currCol < n - 1) s.push(currRow to currCol + 1)
                    }
                }                
                col++
            }
            row++
        }
        return islandCount
    }
}
