# https://leetcode.com/problems/sqrtx/
class Solution:
    def mySqrt(self, x: int) -> int:
        if (x == 0 or x == 1):
            return x

        left = 0
        right = x
        while left < right:
            mid = int((left + right) / 2)
            if (mid * mid == x):
                return mid
            if (mid * mid > x):
                right = mid
            else:
                inc = mid + 1
                if (inc * inc > x):
                    return mid
                else:
                    left = mid
