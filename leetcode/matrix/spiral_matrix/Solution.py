# Submission: https://leetcode.com/problems/spiral-matrix/submissions/1132483271
class Solution:
    def spiralOrder(self, matrix: List[List[int]]) -> List[int]:
        m, n = len(matrix), len(matrix[0])
        ret, level = [], 0
        i, j, d, k = 0, 0, 'R', 0
        num = m * n
        while k < num:
            ret.append(matrix[i][j])
            match d:
                case 'R':
                    j += 1
                    if j == n - level:
                        j -= 1
                        i += 1
                        d = 'D'
                case 'D':
                    i += 1
                    if i == m - level:
                        i -= 1
                        j -= 1
                        d = 'L'
                case 'L':
                    j -= 1
                    if j == -1 + level:
                        j += 1
                        i -= 1
                        d = 'U'
                case 'U':
                    i -= 1
                    if i == 0 + level:
                        i += 1
                        j += 1
                        d = 'R'
                        level += 1
                case _: raise Exception("Invalid case")
            k += 1
        return ret
                
