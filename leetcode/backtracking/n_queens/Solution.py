# https://leetcode.com/problems/n-queens/submissions/1146644802
class Solution:
    def solveNQueens(self, n: int) -> List[List[str]]:
        # 0: available, num: num queens making this spot unavailable
        availability = [[0 for _ in range(n)] for __ in range(n)]  
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
                    if availability[r][c] == 0:
                        board[r][c] = 'Q'
                        def update_availability(val):
                            for i in range(r, n):
                                availability[i][c] += val
                            for d in range(1, n-r):
                                if c - d >= 0:
                                    availability[r+d][c-d] += val
                                if c + d <= n - 1:
                                    availability[r+d][c+d] += val
                        update_availability(1)
                        solve(r+1, q+1)
                        # backtrack:
                        update_availability(-1)
                        board[r][c] = '.'
        solve(0,0)
        return ans

                
