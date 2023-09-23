# https://leetcode.com/problems/symmetric-tree/
# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def isSymmetric(self, root: Optional[TreeNode]) -> bool:
        return areSymmetric(root.left, root.right)

def areSymmetric(r1: TreeNode, r2: TreeNode) -> bool:
    if r1 == None or r2 == None:
        if r1 == None and r2 == None:
            return True
        else:
            return False
    if r1.val != r2.val:
        return False
    return areSymmetric(r1.left, r2.right) and areSymmetric(r1.right, r2.left)
