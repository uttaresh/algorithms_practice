// https://leetcode.com/problems/dungeon-game/submissions/
class Solution {
    private lateinit var dungeon: Array<IntArray>
    
    fun calculateMinimumHP(dungeon: Array<IntArray>): Int {
        this.dungeon = dungeon
        
        return 1 + maxOf(0, minimumHealthCostToEnd_bottomup())
    }
    
    private fun minimumHealthCostToEnd_bottomup(): Int {
        val row = IntArray(dungeon[0].size)
        for (i in dungeon.size - 1 downTo 0) {
            for (j in dungeon[0].size - 1 downTo 0) {
                val cellCost = dungeon[i][j] * -1
                if (i == dungeon.size - 1 && j == dungeon[0].size - 1) {
                    row[j] = cellCost
                } else {
                    val downCost = if (i < dungeon.size - 1) row[j] else Int.MAX_VALUE
                    val rightCost = if (j < dungeon[0].size - 1) row[j + 1] else Int.MAX_VALUE
                    val remCost = minOf(downCost, rightCost)
                    row[j] = maxOf(cellCost, cellCost + remCost)
                }
            }
            //println(row.joinToString())
        }        
        return row[0]        
    }
}
