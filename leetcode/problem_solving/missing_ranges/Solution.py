# https://leetcode.com/problems/missing-ranges/
class Solution:
    def findMissingRanges(self, nums: List[int], lower: int, upper: int) -> List[List[int]]:
        missing = []
        prev = lower-1
        nums.append(upper+1)
        for num in nums:
            if prev != num - 1:
                missing.append([prev + 1, num - 1])
            prev = num
        return missing
