class Solution {
    fun canCross(stones: IntArray): Boolean {
        return canCross(stones, 1, 0)
    }

    private fun canCross(stones: IntArray, k: Int, start: Int): Boolean {
        val params = Pair(k, start)
        if (params !in memo) {
            if (k == 0) return false // k = 0 means last jump was zero, invalid step
            if (!isStonePresent(stones, start)) return false
            if (start == stones.last()) return true
            memo[params] = canCross(stones, k - 1, start + k - 1) ||
                    canCross(stones, k, start + k) ||
                    canCross(stones, k + 1, start + k + 1)
        }
        return memo[params]!!
    }

    private fun isStonePresent(stones: IntArray, pos: Int): Boolean {
        return binarySearch(stones, pos, 0, stones.size - 1)
    }

    // Perform a binary search to find if an element exists
    private fun binarySearch(arr: IntArray, target: Int, start: Int, end: Int): Boolean {
        if (start > end) return false
        val mid = (start + end) / 2
        if (target == arr[mid]) {
            return true
        } else if (target < arr[mid]) {
            return binarySearch(arr, target, start, mid - 1)
        } else {
            return binarySearch(arr, target, mid + 1, end)
        }
    }

    companion object {
        private val memo = mutableMapOf<Pair<Int, Int>, Boolean>()
    }
}
