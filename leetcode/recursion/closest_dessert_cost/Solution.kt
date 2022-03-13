class Solution {
    fun closestCost(baseCosts: IntArray, toppingCosts: IntArray, target: Int): Int {
        var bestCost = baseCosts[0]
        var bestDistance = distance(target, bestCost)
        for (baseCost in baseCosts) {
            val currBestCost = baseCost + bestToppingsCost(target - baseCost, toppingCosts, 0)
            val currBestDistance = distance(target, currBestCost)
            if (currBestDistance < bestDistance) {
                bestDistance = currBestDistance
                bestCost = currBestCost
            } else if (currBestDistance == bestDistance) bestCost = minOf(bestCost, currBestCost)
        }
        return bestCost
    }

    private fun bestToppingsCost(target: Int, toppingCosts: IntArray, start: Int): Int {
        if (start >= toppingCosts.size || target <= 0) return 0

        val currCost = toppingCosts[start]

        val skip = bestToppingsCost(target, toppingCosts, start + 1)
        val once = currCost + bestToppingsCost(target - currCost, toppingCosts, start + 1)
        val twice = currCost + currCost + bestToppingsCost(target - currCost - currCost, toppingCosts, start + 1)

        val distanceSkip = distance(target, skip)
        val distanceOnce = distance(target, once)
        val distanceTwice = distance(target, twice)

        if (distanceSkip < distanceOnce && distanceSkip < distanceTwice) return skip
        if (distanceOnce < distanceSkip && distanceOnce < distanceTwice) return once
        if (distanceTwice < distanceSkip && distanceTwice < distanceOnce) return twice

        if (distanceSkip == distanceOnce) return minOf(skip, once)
        if (distanceSkip == distanceTwice) return minOf(skip, twice)

        //if (distanceOnce == distanceTwice)
        return minOf(once, twice)
    }

    private fun distance(cost1: Int, cost2: Int) = Math.abs(cost1 - cost2)

}
