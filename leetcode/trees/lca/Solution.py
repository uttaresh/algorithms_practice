class Solution:
    def lowestCommonAncestor(self, root: 'TreeNode', p: 'TreeNode', q: 'TreeNode') -> 'TreeNode':
        ans = None
        def find(curr) -> tuple[bool, bool]:
            if not curr:
                return (False, False)
            lhp, lhq = find(curr.left)
            rhp, rhq = find(curr.right)
            chp, chq = (curr == p, curr == q)
            fp, fq = (lhp or rhp or chp, lhq or rhq or chq)
            if fp and fq:
                nonlocal ans
                if not ans:
                    ans = curr
            return (fp, fq)
        find(root)
        return ans
