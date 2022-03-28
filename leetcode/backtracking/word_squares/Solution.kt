package backtracking.word_squares

import java.util.*

/**
 * Problem: https://leetcode.com/problems/word-squares/
 * Submission: https://leetcode.com/submissions/detail/668746953/
 */
class Solution {
    private val words = Trie()
    private lateinit var grid: Array<CharArray>
    private val wordSquares = ArrayList<List<String>>()

    fun wordSquares(words: Array<String>): List<List<String>> {
        words.forEach { this.words.insert(it) }
        grid = Array(words[0].length) { CharArray(words[0].length) }
        solveGrid(0)
        return wordSquares
    }

    private fun solveGrid(start: Int) {
        if (start == grid.size) {
            // if we've filled the grid in completely, then this is a valid answer!! Record it!
            wordSquares.add(grid.map { String(it) })
            return
        }
        // Try all words that fit at this start point i.e. all words with the prefix we have so far @ (start, start)
        var prefix = ""
        for (i in 0 until start) {
            prefix += grid[start][i]
        }
        val possibleWords = words.find(prefix)
        for (word in possibleWords) {
            for ((i, c) in word.withIndex()) {
                grid[start][i] = c
                grid[i][start] = c
            }
            solveGrid(start + 1)
            // Implicit backtracking -- the next for loop will over-write the grid
        }
    }

    class Trie {
        val root = TrieNode("")

        fun insert(word: String) {
            var node = root
            var pathLen = 0
            for (c in word) {
                val i = ctoi(c)
                if (node.chars[i] == null) {
                    node.chars[i] = TrieNode(word.substring(0, pathLen + 1))
                }
                node = node.chars[i]!!
                pathLen++
            }
            node.word = true
        }

        fun find(prefix: String): List<String> {
            val prefixNode = getNodeAtPath(prefix) ?: return emptyList()
            val words = LinkedList<String>()
            val stack = Stack<TrieNode>()
            stack.add(prefixNode)
            while (stack.isNotEmpty()) {
                val curr = stack.pop()
                if (curr.word) {
                    words.add(curr.path)
                }
                for (child in curr.chars) {
                    if (child != null) {
                        stack.push(child)
                    }
                }
            }
            return words
        }

        private fun getNodeAtPath(path: String): TrieNode? {
            var node = root
            for (c in path) {
                val i = ctoi(c)
                if (node.chars[i] == null) {
                    return null
                } else {
                    node = node.chars[i]!!
                }
            }
            return node
        }

        private fun ctoi(c: Char): Int {
            return c.toInt() - BASE
        }

        companion object {
            private const val BASE = 'a'.toInt()
        }

        // For this specific problem, storing the prefix path in the node makes sense. The additional space simplifies
        // getting all the words for a prefix... simply hit all leaf nodes (and intermediate nodes with word set) and
        // get the path.
        class TrieNode(val path: String) {
            val chars: Array<TrieNode?> = Array(26) { null }
            var word: Boolean = false
        }
    }
}

fun main() {
    val input1 = arrayOf("area","lead","wall","lady","ball")
    val input2 = arrayOf("abat","baba","atan","atal")
    val ret = Solution().wordSquares(input2)
    println(ret)
}

private fun `test trie functionality`() {
    val trie = Solution.Trie().apply {
        insert("apple")
        insert("application")
        insert("appliances")
        insert("cat")
    }
    println(trie.find(""))
    println(trie.find("appli"))
}
