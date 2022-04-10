// https://leetcode.com/problems/palindrome-pairs/
// TODO This solution works, but not when one of the input words is an empty string. LC requires this edge case to work too
package data_structs.tries.palindrome_pairs

import java.util.*
import kotlin.collections.HashMap

class Solution {
    private val root = TrieNode()

    fun palindromePairs(words: Array<String>): List<List<Int>> {
        // insert the reverse of each word into the trie
        for ((i, word) in words.withIndex()) {
            insert(reverse(word), i)
        }

        // form palindromes by joining each word to its suffix palindromes
        val palindromes = words
            .mapIndexed { index, word ->
                findPalindromeSuffixes(word, index) // find palindromic suffixes for each word, e.g. lls -> [sssll]
                    .map { listOf(index, it) } // add the word to each palindromic suffix, e.g. [sssll] -> [llssssll]
            }
            .flatten()
        return palindromes
    }

    private fun findPalindromeSuffixes(word: String, wordIndex: Int): List<Int> {
        var curr = root
        var j = 0
        var path = ""
        val ret = LinkedList<Int>()
        while (j < word.length) {
            val c = word[j]
            if (c !in curr.children) {
                // violation: word doesn't fit in this path
                return emptyList()
            } else {
                path += c
                curr = curr.children[c]!!
            }

            // when the word matches exactly i.e. when the word terminates at exactly a suffix leaf
            if (j == word.length - 1 && (curr.wordIndex != null && curr.wordIndex != wordIndex)) {
                ret.add(curr.wordIndex!!)
            }
            j++
        }

        // return any of the remaining "middle substrings" are palindromes
        if (curr.remPalPaths.isNotEmpty()) {
            curr.remPalPaths
                .forEach { ret.add(it) }
        }

        return ret
    }

    private fun insert(word: String, wordIndex: Int) {
        var curr = root

        for ((i, c) in word.withIndex()) {
            if (c !in curr.children) {
                curr.children[c] = TrieNode()
            }
            val remaining = word.removeRange(0..i)
            if (remaining.isNotEmpty() && isPalindrome(remaining)) {
                curr.children[c]!!.remPalPaths.add(wordIndex)
            }
            curr = curr.children[c]!!
        }
        curr.wordIndex = wordIndex
    }

    private fun isPalindrome(s: String): Boolean {
        var i = 0
        var j = s.length - 1
        while (i < s.length) {
            if (s[i] != s[j]) return false
            i++
            j--
        }
        return true
    }

    private fun reverse(s: String): String {
        val chars = CharArray(s.length)
        val n = s.length
        for (i in 0 until n) {
            chars[i] = s[n - 1 - i]
        }
        return String(chars)
    }

    data class TrieNode(
        val children: HashMap<Char, TrieNode> = HashMap(),
        var wordIndex: Int? = null,
        var remPalPaths: LinkedList<Int> = LinkedList()
    )
}

fun main() {
    val input = arrayOf("abcd", "dcba", "lls", "sssll", "s", "e", "ce")
//    val input = arrayOf("a", "") TODO Doesn't work for empty string cases!
    val ret = Solution().palindromePairs(input)
    println("ret: " + ret.joinToString())
}
