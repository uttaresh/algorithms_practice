class Solution:
    """
    Algorithm:
        Find the first uneven point i.e. heigh discrepancy between consecutive points
        Until the end of array:
            1. Track the previous uneven height
            2. Track the current height
            3. Track the new height
            4. If previous height > current < new, then hill
            5. If previous height < current > new, then valley
    """
    def countHillValley(self, nums: List[int]) -> int:
        x = 1
        while x < len(nums) and nums[x] == nums[x-1]:
            x += 1
        if x == len(nums):
            return 0
        prevUneven = nums[x-1]
        curr = nums[x]
        count = 0
        while x < len(nums):
            new = nums[x]
            if (prevUneven > curr < new) or (prevUneven < curr > new):
                count += 1
            if new != curr:
                prevUneven = curr
                curr = new
            x += 1
        return count
        
