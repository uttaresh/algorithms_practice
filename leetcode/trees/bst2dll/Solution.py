"""
# Definition for a Node.
class Node:
    def __init__(self, val, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right
"""

class Solution:
    def treeToDoublyList(self, root: 'Optional[Node]') -> 'Optional[Node]':
        if not root:
            return root
        head = Node()
        curr = head
        def inorder(node):
            if not node:
                return
            inorder(node.left)
            rightTree = node.right
            nonlocal curr
            curr.right = node
            node.left = curr
            curr = curr.right
            inorder(node.right)
        inorder(root)
        curr.right = head.right
        head.right.left = curr
        return head.right
