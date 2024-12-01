'''
https://leetcode.com/problems/shortest-path-in-a-hidden-grid/
This problem has us first map out an unknown world, and then find the shortest path.
We cannot use BFS from the beginning because we don't have instant access to the
whole map. The "master" is a like a person who can only take one step at a time physically
and cannot have "random access" instantly to the whole grid.
'''

# """
# This is GridMaster's API interface.
# You should not implement it, or speculate about its implementation
# """
#class GridMaster(object):
#    def canMove(self, direction: str) -> bool:
#        
#
#    def move(self, direction: str) -> None:
#        
#
#    def isTarget(self) -> bool:
#        
#

class Solution(object):
    def findShortestPath(self, master: 'GridMaster') -> int:
        N = 1010
        start = N // 2
        # 0: unvisited, -1: blocked, 1: open, 2: target
        grid = [[0 for i in range(N)] for j in range(N)]
        found = False
        def map_grid(r, c, master, grid):
            if grid[r][c] == 1:
                return         
            if master.isTarget():
                nonlocal found
                found = True
                grid[r][c] = 2
                return
            else:
                grid[r][c] = 1
            dirs = {
                'U' : (-1, 0, 'D'),
                'D' : (1, 0, 'U'),
                'L' : (0, -1, 'R'),
                'R' : (0, 1, 'L')
            }
            for d, (rd, cd, opp) in dirs.items():                
                if master.canMove(d):
                    master.move(d)
                    map_grid(r+rd, c+cd, master, grid)
                    master.move(opp)
                else:
                    grid[r+rd][c+cd] = -1
        map_grid(start, start, master, grid)
        if not found:
            return -1
        
        q = deque()
        q.append((start, start, 0))
        visited = set()
        while q:
            r, c, d = q.popleft()
            if grid[r][c] == 2:
                return d
            for (rd, cd) in ((-1, 0), (1, 0), (0, -1), (0, 1)):
                if (r+rd,c+cd) not in visited and grid[r+rd][c+cd] > 0:
                    visited.add((r+rd, c+cd))
                    q.append((r+rd, c+cd, d+1))
        raise Exception("unexpected execution")
