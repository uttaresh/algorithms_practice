# https://leetcode.com/problems/minimum-window-substring 
class Solution:
    def minWindow(self, s: str, t: str) -> str:
        counts, needed = {}, {}
        n = len(s)
        i, j = 0, 0
        shortest = (inf, 0, 0)
        for c in t:
            if c not in needed:
                needed[c] = 0
            needed[c] += 1
        def has_needed():
            for c in needed:
                if c not in counts or counts[c] < needed[c]:
                    return False
            return True
        while j < n:
            while j < n and not has_needed():
                c = s[j]
                if c not in counts:
                    counts[c] = 0
                counts[c] += 1
                j += 1
            j -= 1
            while has_needed():
                win_sz = j + 1 - i
                if win_sz < shortest[0]:
                    shortest = (win_sz, i, j + 1)
                c = s[i]
                counts[c] -= 1
                i += 1
            j += 1
        return s[shortest[1]:shortest[2]]
