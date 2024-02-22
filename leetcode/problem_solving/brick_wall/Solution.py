# https://leetcode.com/problems/brick-wall/submissions/1183321562
class Solution:
    def leastBricks(self, wall: List[List[int]]) -> int:
        freq = defaultdict(int)
        for row in wall:
            offset = 0
            for i in range(len(row)-1): # skip last offset at end of wall, per question definition
                brick_width = row[i]
                offset += brick_width
                freq[offset] += 1
        return len(wall) - max(freq.values(), default=0)
