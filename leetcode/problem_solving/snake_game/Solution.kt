class SnakeGame(val width: Int, val height: Int, val food: Array<IntArray>) {

    var cursor = Coordinate(0, 0)
    var f = 0
    var length = 0
    
    val snake = mutableSetOf(cursor)
    var tailQ = ArrayDeque<Coordinate>().apply{ add(cursor) }
    
    fun move(direction: String): Int {
        val previous = cursor
        cursor = when (direction) {
            "U" -> Coordinate(previous.row - 1, previous.col)
            "D" -> Coordinate(previous.row + 1, previous.col)
            "L" -> Coordinate(previous.row, previous.col - 1)
            "R" -> Coordinate(previous.row, previous.col + 1)
            else -> throw IllegalArgumentException()
        }
        if (cursor.row < 0 || cursor.row >= height || cursor.col < 0 || cursor.col >= width) return -1
        
        val ateFood = f < food.size && food[f][0] == cursor.row && food[f][1] == cursor.col
        
        if (ateFood) {
            f++
            length++ 
        } else {
            val oldTail = tailQ.remove()
            snake.remove(oldTail)       
        }
        
        // check if head is colliding with body else add to snake
        if (cursor in snake) return -1
        else {
            snake.add(cursor)
            tailQ.add(cursor)
        }
        
        //printGameState()
        return length
    }

    fun printGameState() {
        for (i in 0 until height) {
            for (j in 0 until width) {
                if (Coordinate(i, j) in snake) {
                    print('S')
                } else if (f < food.size && food[f][0] == i && food[f][1] == j) {
                    print('F')
                } else {
                    print('X')
                }                
            }    
            println()
        }
        println("---------")
    }
}

data class Coordinate(
    val row: Int,
    val col: Int
)

/**
 * Your SnakeGame object will be instantiated and called as such:
 * var obj = SnakeGame(width, height, food)
 * var param_1 = obj.move(direction)
 */
