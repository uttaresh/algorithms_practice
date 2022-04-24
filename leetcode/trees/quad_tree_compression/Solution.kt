// https://leetcode.com/problems/construct-quad-tree/submissions/
// Similar to image compression problem from college
/**
 * Definition for a QuadTree node.
 * class Node(var `val`: Boolean, var isLeaf: Boolean) {
 *     var topLeft: Node? = null
 *     var topRight: Node? = null
 *     var bottomLeft: Node? = null
 *     var bottomRight: Node? = null
 * }
 */

class Solution {
    fun construct(grid: Array<IntArray>): Node? {
        return construct(grid, 0, 0, grid.size)
    }
    
    fun construct(grid: Array<IntArray>, row: Int, col: Int, size: Int): Node? {
        val firstValue = grid[row][col]
        var allSame = true
        for (i in row .. row + size - 1) {
            for (j in col .. col + size - 1) {
                if (grid[i][j] != firstValue) {
                    allSame = false
                    break
                }
            }
            if (!allSame) break
        }
        return if (allSame) {
            Node(firstValue == 1, true)
        } else {
            Node(false, false).apply {
                val childSize = size / 2
                topLeft = construct(grid, row, col, childSize)
                topRight = construct(grid, row, col + childSize, childSize)
                bottomLeft = construct(grid, row + childSize, col, childSize)
                bottomRight = construct(grid, row + childSize, col + childSize, childSize)
            }
        }
    }
}
