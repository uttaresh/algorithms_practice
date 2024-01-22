class BottomUp1DDPSolution:
    # This solution will make no sense just by looking at it. The only way to
    # understand it is to start from the simpler top down solution, and then
    # slowly make iterative improvements to get to 2D bottom-up DP and then
    # finally 1D bottom up, and then another tweak for reducing the range of
    # j at the end.
    def change(self, amount: int, coins: List[int]) -> int:
        dp = [0 for _ in range(amount+1)]
        dp[0] = 1
        for i in reversed(range(len(coins))):
            for j in range(coins[i],amount+1):
                dp[j] += dp[j-coins[i]]
        return dp[-1]

class TopDownSolution:
    # The trick in this one is to return combinations of change, not
    # permutations (jumbling allowed). To do this, think of it this way:
    # If we sorted the coins by size (1, 2, 5), then we could restrict
    # going down only to the path 1-1-2-5 and not 1-2-1-5 by requiring
    # that we only consider coins of equal or greater value than where
    # we currently are. So if we are at a path start with coin 2, we 
    # cannot go down a path involving coin 1, as that will have been
    # taken care of by an earlier path.
    def change(self, amount: int, coins: List[int]) -> int:
        coins.sort()
        @cache
        def combinations(i, amount):
            if amount == 0:
                return 1
            ways = 0
            for j in range(i, len(coins)):
                rem = amount - coins[j]
                if rem < 0:
                    break
                ways += combinations(j, rem)
            return ways
        return combinations(0, amount)
