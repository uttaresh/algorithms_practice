# Optimized from previous version (see history)
# Removed the 2D availability board, since:
# 1. We don't need to track availability for the rows. We're only moving downwards after placing queens
# 2. Availability in cols can be tracked with a 1D set/array
# 3. Availability in diags can be tracked with a unique identifier for diagonals in both directions
# 
# I was able to come up with the strategy independently but tried to use arrays, and the math became
# quickly over-complicated. Then looked at Neetcode's solution and noticed that the only difference
# was he was using a set for the diagonals since that allows the diagonal ID to be negative, making
# the task much easier. 
# https://leetcode.com/problems/n-queens/submissions/1147052558
class Solution:
    def solveNQueens(self, n: int) -> List[List[str]]:
        cols = set()
        up_diags = set()
        down_diags = set()
        
        board = [['.' for _ in range(n)] for __ in range(n)]
        ans = []
        def solve(r, q):
            if q == n:                    
                ans.append([''.join(row) for row in board])
                return
            else:
                if r >= n:
                    return # no more spots, no solution this way

                # try all available spots in the row
                for c in range(n):
                    available = not (c in cols or r+c in up_diags or r-c in down_diags)
                    if available:
                        board[r][c] = 'Q'
                        cols.add(c)
                        down_diags.add(r-c)
                        up_diags.add(r+c)
                        solve(r+1, q+1)
                        # backtrack:
                        down_diags.remove(r-c)
                        up_diags.remove(r+c)
                        cols.remove(c)
                        board[r][c] = '.'
        solve(0,0)
        return ans

                
