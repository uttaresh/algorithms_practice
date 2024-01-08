# Submission: https://leetcode.com/problems/largest-rectangle-in-histogram/submissions/1140250914 
class Solution:
    def largestRectangleArea(self, heights: List[int]) -> int:
        stack = []
        max_area = 0
        for curr_p, curr_h in enumerate(heights):
            right = curr_p
            left = curr_p
            while len(stack) > 0 and curr_h <= stack[-1][1]:
                left, height = stack.pop()
                width = right - left
                area = height * width
                max_area = max(max_area, area)         
            stack.append((left, curr_h))
        right = len(heights)
        while len(stack) > 0:
            left, height = stack.pop()
            width = right - left
            area = height * width
            max_area = max(max_area, area)
        return max_area
        
