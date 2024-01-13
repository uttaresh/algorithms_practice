class LRUCache:

    def __init__(self, capacity: int):
        self.lut = {}
        self.lru_head = None
        self.lru_tail = None
        self.capacity = capacity

    def __repr__(self):
        return f"LRUCache; sz: {len(self.lut)}, cap: {self.capacity}, head: {self.lru_head}, tail: {self.lru_tail}"

    def get(self, key: int) -> int:
        lut = self.lut
        if key not in lut:
            return -1
        self.set_front(lut[key])
        return lut[key].val

    def set_front(self, node):
        if node == self.lru_head:
            return
        if node == self.lru_tail and node.prev:
            self.lru_tail = node.prev
        prev = node.prev
        nxt = node.nxt
        if prev:
            prev.nxt = nxt
        if nxt:
            nxt.prev = prev
        node.nxt = self.lru_head
        node.prev = None
        if self.lru_head:
            self.lru_head.prev = node
        else:
            if not self.lru_tail:
                self.lru_tail = node
        self.lru_head = node

    def put(self, key: int, value: int) -> None:
        lut = self.lut
        if key not in lut:
            node = DLLNode(key, value, self.lru_head, None)
            lut[key] = node
        else:
            node = lut[key]
            node.val = value
        self.set_front(node)
        if len(lut) > self.capacity:
            self.evict()

    def evict(self):
        del self.lut[self.lru_tail.key]
        if self.lru_tail.prev:
            self.lru_tail.prev.next = None
        self.lru_tail = self.lru_tail.prev


class DLLNode:
    def __init__(self, key, val, nxt, prev):
        self.key = key
        self.val = val
        self.nxt = nxt
        self.prev = prev

    def __repr__(self):
        return "{" + str(self.key) + ":" + str(self.val) + "}"

# Your LRUCache object will be instantiated and called as such:
# obj = LRUCache(capacity)
# param_1 = obj.get(key)
# obj.put(key,value)

def main():
    # in: [[2],[2,1],[3,2],[3],[2],[4,3],[2],[3],[4]]
    # exp: [null,null,null,2,1,null,1,-1,3]
    cache = LRUCache(2)
    out = [
        None,
        cache.put(2, 1),
        cache.put(3, 2),
        cache.get(3),
        cache.get(2),
        cache.put(4, 3),
        cache.get(2),
        cache.get(3),
        cache.get(4)
    ]
    print(*out)

main()