class Solution:
    # Not at all understandable just by reading. Only way is to draw
    # out a 2D DP grid for target and index. The target has to be
    # allowed to be grow out of "bounds" i.e. to the max value
    # allowed by the sum of all numbers added to the target (imagine
    # all nums as positive are added. We can trim this width/range
    # down as we progress up the "tree" (again, have to draw it out).
    #
    # I feel pretty good about having done this... didn't need any
    # help at all, figured it out on my own tho it was quite
    # challenging xD
    def findTargetSumWays(self, nums: List[int], target: int) -> int:
        width = sum(nums)
        dp = defaultdict(int)
        dp[0] = 1
        for i in reversed(range(len(nums))):
            next_dp = defaultdict(int)
            for j in range(target-width, target+width+1):
                next_dp[j] = dp[j-nums[i]] + dp[j+nums[i]]
            dp = next_dp
            width -= nums[i]
        return dp[target]
