class Solution:
    # https://leetcode.com/problems/word-break/submissions/1127779441
    def wordBreak(self, s: str, wordDict: List[str]) -> bool:
        @cache
        def can_break(n):
            if n == 0:
                return True
            for j in reversed(range(n)):
                suffix = s[j:n]
                if suffix in wordDict:
                    if can_break(j):
                        return True
            return False
        return can_break(len(s))
