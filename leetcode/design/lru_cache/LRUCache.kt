class LRUCache(private val capacity: Int) {
    val storage = HashMap<Int, Pair<Int, LRUListNode>>() // cache key -> (cache val, lru node)
    var size = 0
    var head: LRUListNode? = null
    var tail: LRUListNode? = null

    init {
        require(capacity > 0)
    }

    fun get(key: Int): Int {
        //println("Fetching $key")
        //printState()
        if (key in storage){
            // update LRU list
            markMostRecentlyUsed(storage[key]!!.second)
            return storage[key]!!.first
        }
        return -1
    }

    fun put(key: Int, value: Int) {
        //printState()
        //println("Adding $key=$value")
        if (key !in storage) { 
            // add if not present
            if (size == capacity) evictLeastRecentlyUsed()
            if (head == null) {
                head = LRUListNode(key, null, null)
                tail = head
                storage[key] = Pair(value, head!!)
            } else {
                tail!!.next = LRUListNode(key, tail, null)
                tail = tail!!.next
                storage[key] = Pair(value, tail!!)
            }            
            size++
        } else {
            // update cache value and mark most recently used
            val lruNode = storage[key]!!.second
            markMostRecentlyUsed(lruNode)
            storage[key] = Pair(value, lruNode)
        }
    }

    private fun markMostRecentlyUsed(lruNode: LRUListNode) {
        if (lruNode != tail) { // if not already most recently used
            //println(lruNode.key)
            if (lruNode == head) {
                head = head!!.next
            } else {
                lruNode.prev!!.next = lruNode.next
                lruNode.next!!.prev = lruNode.prev
            }
            tail!!.next = lruNode
            lruNode.prev = tail
            tail = lruNode
            lruNode.next = null
        }
        //println("Most recently used updated...")
        //printState()
    }
    
    private fun evictLeastRecentlyUsed() {
        checkNotNull(storage.remove(head!!.key))
        head = head!!.next
        size--
    }
}

data class LRUListNode(
    val key: Int,
    var prev: LRUListNode?,
    var next: LRUListNode?
)

/**
 * Your LRUCache object will be instantiated and called as such:
 * var obj = LRUCache(capacity)
 * var param_1 = obj.get(key)
 * obj.put(key,value)
 */
fun main() {
//    ["LRUCache","put","put","put","put","get","get","get","get","put","get","get","get","get","get"]
//    [[3],[1,1],[2,2],[3,3],[4,4],[4],[3],[2],[1],[5,5],[1],[2],[3],[4],[5]]

    val cache = LRUCache(3)
    cache.apply {
        put(1, 1)
        put(2, 2)
        put(3, 3)
        put(4, 4)
        get(4)
        get(3)
        get(2)
        get(1)
        put(5,5)
        get(1)
        get(2)
        get(3)
        get(4)
    }
}
