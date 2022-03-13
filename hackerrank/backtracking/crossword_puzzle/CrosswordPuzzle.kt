import java.io.*
import java.math.*
import java.security.*
import java.text.*
import java.util.*
import java.util.concurrent.*
import java.util.function.*
import java.util.regex.*
import java.util.stream.*
import kotlin.collections.*
import kotlin.comparisons.*
import kotlin.io.*
import kotlin.jvm.*
import kotlin.jvm.functions.*
import kotlin.jvm.internal.*
import kotlin.ranges.*
import kotlin.sequences.*
import kotlin.text.*

/*
 * Complete the 'crosswordPuzzle' function below.
 *
 * The function is expected to return a STRING_ARRAY.
 * The function accepts following parameters:
 *  1. STRING_ARRAY crossword
 *  2. STRING words
 */

fun crosswordPuzzle(crossword: Array<String>, words: String): Array<String> {
    val wordsList = words.split(";").map { it.toCharArray() }
    val crosswordChars = crossword.map { it.toCharArray() }.toTypedArray()
    val resp = solve(crosswordChars, wordsList, 0) ?: throw IllegalArgumentException("No solution for game!")
    return resp.map { String(it) }.toTypedArray()
}

private fun solve(crossword: Array<CharArray>, words: List<CharArray>, startRow: Int): Array<CharArray>? {
    val blank = findNextBlank(crossword, startRow) ?: return crossword
    if (isHorizontalSlot(crossword, blank.first, blank.second)) {
        val row = blank.first
        val startIndex = getHorizontalSlotStart(crossword, row, blank.second)
        var endIndex = startIndex
        while (endIndex < crossword[0].size - 1 && crossword[row][endIndex+1] != BLOCKED) endIndex++
        val wordSize = endIndex - startIndex + 1
        val possibleWords = words.filter { it.size == wordSize }
        val original = crossword[row].sliceArray(startIndex..endIndex)
        for (word in possibleWords) {
            for (i in 0 until wordSize) {
                val crosswordChar = crossword[row][startIndex+i]
                if (crosswordChar != BLANK && crosswordChar != word[i]) {
                    // pre-existing char doesn't match, backtrack and skip
                    for (i in 0 until wordSize) {
                        crossword[row][startIndex+i] = original[i]
                    }
                    break
                }
                crossword[row][startIndex+i] = word[i]
            }
            val solution = solve(crossword, words.minus(word), row)
            // if found, return solution
            if (solution != null) {
                printCrossword(solution)
                return solution
            }
            // else backtrack
            for (i in 0 until wordSize) {
                crossword[row][startIndex+i] = original[i]
            }
        }
        // no solutions found with this input
        return null
    } else {
        val col = blank.second
        val startIndex = getVerticalSlotStart(crossword, blank.first, col)
        var endIndex = startIndex
        while (endIndex < crossword.size - 1 && crossword[endIndex+1][col] != BLOCKED) endIndex++
        val wordSize = endIndex - startIndex + 1
        val possibleWords = words.filter { it.size == wordSize }
        val original = CharArray(wordSize) { '@' }
        for (i in 0 until wordSize) original[i] = crossword[startIndex+i][col]
        for (word in possibleWords) {
            for (i in 0 until wordSize) {
                val crosswordChar = crossword[startIndex+i][col]
                if (crosswordChar != BLANK && crosswordChar != word[i]) {
                    // pre-existing char doesn't match, backtrack and skip
                    for (i in 0 until wordSize) {
                        crossword[startIndex+i][col] = original[i]
                    }
                    break
                }
                crossword[startIndex+i][col] = word[i]
            }
            val solution = solve(crossword, words.minus(word), startRow)
            // if found, return solution
            if (solution != null) {
                printCrossword(solution)
                return solution
            }
            // else backtrack
            for (i in 0 until wordSize) {
                crossword[startIndex+i][col] = original[i]
            }
        }
        // no solutions found with this input
        return null
    }
}

private fun isHorizontalSlot(crossword: Array<CharArray>, row: Int, col: Int): Boolean {
    // if blank is on right edge, then this should be end of a horizontal word
    if (col == crossword[0].size - 1) return crossword[row][col-1] != BLOCKED

    // if blank is on left edge, then this should be start of a horizontal word
    if (col == 0) return crossword[row][col+1] != BLOCKED

    // if in middle, then either the previous or next horizontal position (or both) should be chars
    return crossword[row][col-1] != BLOCKED || crossword[row][col+1] != BLOCKED
}

private fun getHorizontalSlotStart(crossword: Array<CharArray>, row: Int, col: Int): Int {
    var start = col
    while (start != -1 && crossword[row][start] != BLOCKED) start--
    return start+1
}

private fun getVerticalSlotStart(crossword: Array<CharArray>, row: Int, col: Int): Int {
    var start = row
    while (start != -1 && crossword[start][col] != BLOCKED) start--
    return start+1
}
private fun findNextBlank(crossword: Array<CharArray>, startRow: Int): Pair<Int, Int>? {
    for (i in startRow until crossword.size) {
        for (j in 0 until crossword[0].size) {
            if (crossword[i][j] == BLANK) {
                return i to j
            }
        }
    }
    return null
}

private fun printCrossword(crossword: Array<CharArray>) {
    val printEnabled = false
    if (!printEnabled) return
    println("Printing crossword...")
    for (row in crossword) {
        println(String(row))
    }
    println("Printed crossword...")
}

private const val BLOCKED = '+'
private const val BLANK = '-'

fun main(args: Array<String>) {

    val crossword = Array<String>(10, { "" })
    for (i in 0 until 10) {
        val crosswordItem = readLine()!!
        crossword[i] = crosswordItem
    }

    val words = readLine()!!

    val result = crosswordPuzzle(crossword, words)

    println(result.joinToString("\n"))
}
