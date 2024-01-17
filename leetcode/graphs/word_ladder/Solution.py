class Solution:
    def ladderLength(self, beginWord: str, endWord: str, wordList: List[str]) -> int:
        nodes = {}
        end_found = False
        wordList.append(beginWord)
        for w in wordList:           
            nodes[w] = set()
            if w == endWord:
                end_found = True
        if not end_found:
            return 0     
        for w in wordList:
            for i in range(len(w)):
                mask = w[:i] + '*' + w[i+1:]  # did not get this trick on my own, had to look it up
                nodes[w].add(mask)
                if mask not in nodes:
                    nodes[mask] = set()
                nodes[mask].add(w)
        visited = {}
        q = Deque()
        q.append((beginWord, 1))
        q.append((endWord, -1))
        
        while q:
            curr, dist = q.popleft()            
            visited[curr] = dist            
            for neighb in nodes[curr]:
                if neighb in visited:
                    if visited[neighb] * dist < 0:
                        path_len = abs(visited[neighb]) + abs(dist)                        
                        return (path_len + 1) // 2
                if neighb not in visited:
                    if dist > 0:
                        q.append((neighb, dist+1))
                    else:
                        q.append((neighb, dist-1))
        return 0
            
            
