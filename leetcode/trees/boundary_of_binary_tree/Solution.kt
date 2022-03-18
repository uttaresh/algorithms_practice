/**
 * Example:
 * var ti = TreeNode(5)
 * var v = ti.`val`
 * Definition for a binary tree node.
 * class TreeNode(var `val`: Int) {
 *     var left: TreeNode? = null
 *     var right: TreeNode? = null
 * }
 */
class Solution {
    fun boundaryOfBinaryTree(root: TreeNode?): List<Int> {
        if (root == null) return emptyList()
        val boundary = mutableListOf<Int>()
        
        boundary.add(root.`val`)
        
        // add left boundary
        val s = Stack<TreeNode>()
        if (root.left != null) {
            s.push(root.left)
            while (s.isNotEmpty()) {
                val curr = s.pop()
                boundary.add(curr.`val`)
                if (curr.left != null) {
                    s.push(curr.left)
                } else if (curr.right != null) {
                    s.push(curr.right)
                } else {
                    boundary.removeAt(boundary.size - 1)
                }
            }
        }
        
        // add leaves
        if (root.right != null) s.push(root.right)
        if (root.left != null) s.push(root.left)        
        while (s.isNotEmpty()) {
            val curr = s.pop()            
            if (curr.right != null) {
                s.push(curr.right)
            } 
            if (curr.left != null) {
                s.push(curr.left)
            }
            if (curr.left == null && curr.right == null) {
                boundary.add(curr.`val`)
            }
        }
        
        
        
        // add reverse of right boundary        
        if (root.right != null) {
            var top = root.right
            s.push(top)
            while (true) {                                
                if (top.right != null) {
                    s.push(top.right)
                    top = top.right
                } else if (top.left != null) {
                    s.push(top.left)
                    top = top.left
                } else {
                    s.pop()
                    break
                }                
            }
            while (s.isNotEmpty()) {
                boundary.add(s.pop().`val`)
            }
        }
        
        return boundary.toList()
    }
}
