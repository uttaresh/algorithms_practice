class Solution:
    # Improved strategy: Think of this as dependency ordering. Every ticket
    # provides a hint of which source city should come before a destination
    # city, but _also_ the inverse: which destination city _must_ come after
    # a source city. So in a way, if we flip the direction of time in the
    # universe, each source _depends_ on the destination provided in the ticket.
    # Our problem then becomes to provide the sorted order of dependencies which
    # must start from "JFK" as the problem defines it as the starting point. 
    # We can then solve this easily using topological sort.
    def findItinerary(self, tickets: List[List[str]]) -> List[str]:
        nodes = {}
        for consumer, producer in tickets:
            if consumer not in nodes:
                nodes[consumer] = []
            nodes[consumer].append(producer)
        for k in nodes:
            nodes[k].sort(reverse=True)
        route = []        
        # We cannot use a visited set since the test cases have duplicate tickets.
        # So instead we will remove tickets that we have used, as we use them. To
        # avoid O(n) for each remove, we will pop from the end after sorting in
        # reverse (since we are going backward in time) to meet the requirement of
        # favoring flights to destinations in alphabetical order
        def traverse(consumer):                        
            producers = nodes[consumer] if consumer in nodes else None
            while producers:
                producer = producers.pop()
                traverse(producer)
            route.append(consumer)
            if consumer in nodes:
                del nodes[consumer]
        traverse("JFK")
        return reversed(route)

