# In hindsight, this problem would have been a lot easier to do with a modern, structured
# like Kotlin which encourages typing, structured data, and immutability. I spent an hour
# fixing all the places I had accidentally passed in the wrong number or accidentally
# overridden a value that should have been final/immutable.
# https://leetcode.com/problems/design-twitter/submissions/1145436528
class Twitter:
    # strategy: 
    # 1. each person's tweets are stored in a PQ (least recent first i.e. max time first)
    # 2. post a tweet to your own deq
    # 3. to get news feed:
    #    i) build an uber PQ pointing to head of followee's deqs
    #   ii) pop uber PQ's head and then pop from that followee's deq. update uber deq
    #  iii) save the followee's deq elem for re-adding later
    #   iv) keep repeating until 10 tweets popped
    #    v) add the 10 tweets back to their corresponding followee's deqs
    #   vi) return the 10 tweets

    def __init__(self):
        self.follows = {}
        self.tweets = {}
        self.timestamp = 0
        
    def postTweet(self, userId: int, tweetId: int) -> None:
        self.timestamp += 1 # in this universe, time stands still until a tweet is posted so we can solve this problem more easily xD
        tweets = self.tweets
        if userId not in tweets:
            tweets[userId] = []
        tweets[userId].append((self.timestamp, tweetId))
        

    def getNewsFeed(self, userId: int) -> List[int]:
        follows = self.follows
        if userId not in follows:
            follows[userId] = set([userId])
        tweets = self.tweets
        ret = []
        subs = follows[userId]
        pq = []
        for sub in subs:
            persons_tweets = tweets[sub] if sub in tweets else None
            if persons_tweets:
                timestamp, tweetId = persons_tweets[-1]  # most recent tweet
                pq.append((-timestamp, sub))
        heapify(pq)
        popped = []
        for _ in range(10):
            if not pq:
                break
            _, sub = heappop(pq)
            timestamp, tweetId = tweets[sub].pop()
            ret.append(tweetId)
            popped.append((sub, timestamp, tweetId))
            if tweets[sub]:
                timestamp, tweetId = tweets[sub][-1]
                heappush(pq, (-timestamp, sub))            
        while popped:
            sub, timestamp, tweetId = popped.pop()
            tweets[sub].append((timestamp, tweetId))
        return ret


    def follow(self, followerId: int, followeeId: int) -> None:
        follows = self.follows
        if followerId not in follows:
            follows[followerId] = set([followerId])
        follows[followerId].add(followeeId)
        

    def unfollow(self, followerId: int, followeeId: int) -> None:
        follows = self.follows
        if followerId in follows and followeeId in follows[followerId]:
            follows[followerId].remove(followeeId)


# Your Twitter object will be instantiated and called as such:
# obj = Twitter()
# obj.postTweet(userId,tweetId)
# param_2 = obj.getNewsFeed(userId)
# obj.follow(followerId,followeeId)
# obj.unfollow(followerId,followeeId)
