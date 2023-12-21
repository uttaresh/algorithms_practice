class Solution:
    def coinChange(self, coins: List[int], amount: int) -> int:   
        return self.coin_change_by_queue(coins, amount)

    '''
    Solution using a queue. Runs pretty fast, beat 99.4% for runtime complexity:
    https://leetcode.com/problems/coin-change/submissions/1125207091
    '''
    def coin_change_by_queue(self, coins, amount):
        visited = set()
        q = deque([[0, 0]])
        max_possible = amount // min(coins)
        while q:
            popped = q.popleft()
            sum_so_far, coins_needed = popped[0], popped[1]
            if coins_needed > max_possible:
                return -1
            if sum_so_far == amount:
                return coins_needed
            for c in coins:
                next_sum = sum_so_far + c
                if next_sum not in visited:
                    visited.add(next_sum)
                    q.append([next_sum, coins_needed + 1])
            
