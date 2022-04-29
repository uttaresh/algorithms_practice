// https://leetcode.com/problems/text-justification/
class Solution {
    private lateinit var words: Array<String>
    private var maxWidth: Int = -1
    private val output = LinkedList<String>()
    
    fun fullJustify(words: Array<String>, maxWidth: Int): List<String> {
        this.words = words
        this.maxWidth = maxWidth        
        
        var lineStart = 0 // word idx where curr line starts
        while (true) {
            val lineDetails = getLineDetails(lineStart)
            val isLastLine = lineDetails.lineEnd == words.size - 1
            val isLoneWord = lineDetails.lineEnd == lineDetails.lineStart
            if (isLastLine || isLoneWord) {
                appendLeftAligned(lineDetails)
            } else {
                appendJustified(lineDetails)
            } 
            lineStart = lineDetails.lineEnd + 1
            
            check(lineDetails.lineEnd <= words.size)
            if (lineDetails.lineEnd == words.size - 1) break
        }
        return output
    }
    
    private fun getLineDetails(lineStart: Int): LineDetails {
        var charCount = 0
        var wordIdx = lineStart - 1
        while (wordIdx < words.size - 1 && charCount < maxWidth) {
            wordIdx++
            charCount += words[wordIdx].length
            charCount++ // space                
        }
        charCount-- // remove trailing space

        if (charCount > maxWidth) {
            charCount -= words[wordIdx].length // remove last word
            charCount-- // remove trailing space
            wordIdx--
        }
        return LineDetails(
            lineStart = lineStart,
            lineEnd = wordIdx,
            charCount = charCount
        )
    }
    
    private fun appendJustified(lineDetails: LineDetails) {
        val extraSpacesNeeded = maxWidth - lineDetails.charCount
        val numSeps = lineDetails.lineEnd - lineDetails.lineStart
        val extraSpacesBetweenWords = if (numSeps > 0) extraSpacesNeeded / numSeps else 0
        val extraSpaceUntil = if (numSeps > 0) extraSpacesNeeded % numSeps else 0

        val sb = StringBuilder()     
        for (i in lineDetails.lineStart..lineDetails.lineEnd - 1) {
            sb.append(words[i])
            for (j in 0..extraSpacesBetweenWords) {
                sb.append(' ')
            }
            if (i < extraSpaceUntil + lineDetails.lineStart) sb.append(' ')
        }
        sb.append(words[lineDetails.lineEnd])
        
        output.add(sb.toString())
    }
    
    private fun appendLeftAligned(lineDetails: LineDetails) {
        val sb = StringBuilder()
        for (i in lineDetails.lineStart..lineDetails.lineEnd) {
            sb.append(words[i])            
            if (i != lineDetails.lineEnd) {
                sb.append(' ')
            }            
        }
        val paddingNeeded = maxWidth - lineDetails.charCount
        for (i in 1..paddingNeeded) sb.append(' ')
        
        output.add(sb.toString())
    }
    
    private data class LineDetails(
        val lineStart: Int,
        val lineEnd: Int,
        val charCount: Int
    )
}
