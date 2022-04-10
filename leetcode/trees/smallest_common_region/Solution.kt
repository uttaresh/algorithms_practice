// https://leetcode.com/problems/smallest-common-region
// https://leetcode.com/submissions/detail/677494610/
class Solution {
    private val parents = HashMap<String, String>()
    
    fun findSmallestRegion(regions: List<List<String>>, region1: String, region2: String): String {        
        for (rList in regions) {
            val topRegion = rList[0]
            for (i in 1 until rList.size) {
                val sr = rList[i]
                parents[sr] = topRegion
            }
        }
        val pathToRegion1 = getPath(region1)
        val pathToRegion2 = getPath(region2)
        
        var pos = -1
        val shorterLength = minOf(pathToRegion1.size, pathToRegion2.size)
        for (i in 0 until shorterLength) {
            if (pathToRegion1[i] != pathToRegion2[i]) {
                pos = i - 1
                break
            }
        }
        if (pos < 0) pos = shorterLength - 1
        
        return pathToRegion1[pos]        
    }
    
    fun getPath(r: String): List<String> {
        val path = LinkedList<String>()
        var curr: String? = r
        while (curr != null) {
            path.add(0, curr)
            curr = parents[curr]
        }
        return path
    }
}
