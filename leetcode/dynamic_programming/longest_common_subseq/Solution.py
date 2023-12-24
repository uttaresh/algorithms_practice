class Solution:
    # Beats 96% runtime, 90.8% memory using DP with 1D array
    # https://leetcode.com/problems/longest-common-subsequence/submissions/1127716668
    def longestCommonSubsequence(self, text1: str, text2: str) -> int:
        n1, n2 = len(text1), len(text2)
        arr1 = [0 for j in range(n2+1)]
        arr2 = [0 for j in range(n2+1)]
        for i in range(1, n1+1):
            for j in range(1, n2+1):
                if text1[i-1] == text2[j-1]:
                    arr2[j] = 1 + arr1[j-1]
                else:
                    arr2[j] = max(arr2[j-1], arr1[j])
            for j in range(1, n2+1):
                arr1[j] = arr2[j]
        return arr2[-1]

