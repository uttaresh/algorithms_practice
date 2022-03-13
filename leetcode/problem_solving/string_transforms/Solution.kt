class Solution {
    fun canConvert(str1: String, str2: String): Boolean {
        if (str1.length != str2.length) return false
        if (str1 == str2) return true
        
        // Build map of needed transformations. If a src requires multiple destinations, no solution is possible
        val needed = mutableMapOf<Char, Char>()
        for (i in str1.indices) {
            val src = str1[i]
            val dest = str2[i]
            if (src in needed && needed[src] != dest) return false
            needed[src] = dest
        }
        val chars = str1.toCharArray()
        val blocked = mutableMapOf<Char, Char>()
        for ((src, dest) in needed) {
            // punt values where the dest is in current srcs (ignore when src and dest are the same)
            if (dest in blocked.values) continue
            if (dest in needed.keys && needed[dest] != dest) {
                blocked[src] = dest
                continue
            }
            // change alls matching src chars to dest
            for (i in chars.indices) {
                val c = chars[i]
                if (c == src) chars[i] = dest                
            }
            // if there were any blocked dest chars waiting on this src, unblock them
            for ((blocked_src, blocked_dest) in blocked) {
                if (blocked_dest == src) {
                    needed[blocked_src] = blocked_dest
                    blocked.remove(blocked_src)
                }
            }
        }
        // if some transformations never got unblocked, see if there are unused chars we can leverage as an intermittent transformation
        // it's confusing why we use str2 and not str1 for this, but try it out with a small alphabet (A-D) and you'll see it works
        if (blocked.isNotEmpty()) {
            val available = mutableSetOf<Char>()
            for (c in 'a'..'z') available.add(c)
            for (c in str2) available.remove(c)
            if (available.isEmpty()) return false            
        }
        return true
    }
}
