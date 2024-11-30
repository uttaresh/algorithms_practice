# https://leetcode.com/problems/largest-number-after-digit-swaps-by-parity/ 
class Solution:
    def largestInteger(self, num: int) -> int:
        oq, eq = [], []
        op, ep = set(), set()
        i = 0
        while num:
            digit = num % 10
            num //= 10
            if digit % 2 == 0:
                heapq.heappush(eq, -digit)
                ep.add(i)
            else:
                heapq.heappush(oq, -digit)
                op.add(i)
            i += 1
        i -= 1 # last one in should be first one out
        num = 0
        while eq or oq:
            digit = None
            if i in op:
                digit = -heapq.heappop(oq)
            else:
                digit = -heapq.heappop(eq)
            num = num * 10 + digit
            i -= 1
        return num
