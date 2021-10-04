// Time Complexity :O(nlogk) n = no of users user is following , logk = pq 
// Space Complexity : O(h) no of nested lists
// Did this code successfully run on Leetcode :Yes
// Any problem you faced while coding this : 


// Your code here along with comments explaining your approach
// make class tweet with properies id n createdAt
// HashMap of (userID n HashSet of userid's Tweets)
// HashMap of (userid(Intger) and hashSet of users the userId is following) 

class Twitter {

    int userID;
    //List<Integer> tweets;
    HashMap<Integer,HashSet<Integer>> users;
    HashMap<Integer,HashSet<Tweet>> tweets;
    int times;
    class Tweet
    {
        int TweetId;
        int createdAt;
        public Tweet(int id, int time)
        {
            this.TweetId = id;
            this.createdAt = time;
        }
    }
    public Twitter() {
        users = new HashMap<>();
        tweets = new HashMap<>();
        times = 1;
    }
    
    public void postTweet(int userId, int tweetId) {
       // times++;
        Tweet tweet = new Tweet(tweetId,times++);
        if(users.containsKey(userId))
        {
            if(!users.get(userId).contains(userId))
                follow(userId,userId);
        }
         else
        {
            follow(userId,userId);
        }
        
        if(tweets.containsKey(userId))
        {
              HashSet<Tweet> set =  tweets.get(userId);
            
            set.add(tweet);
            tweets.put(userId,set);
        }
        else
        {    HashSet<Tweet> set =  new HashSet<>();
             set.add(tweet);
            tweets.put(userId,set);
        }
        
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result  = new ArrayList<>();
        
        PriorityQueue<Tweet> pq = new PriorityQueue<>((a,b)->a.createdAt -b.createdAt);
        HashSet<Integer> userFollowingto =  users.get(userId);
        HashSet<Tweet> myFeeds;
        if(userFollowingto != null && userFollowingto.size()>0)
        {
        for(Integer user : userFollowingto)
        {
            myFeeds = tweets.get(user);
            if(myFeeds != null && myFeeds.size()>0)
            for(Tweet feed: myFeeds )
            {
                pq.add(feed);
                if(pq.size()>10)
                {
                    pq.poll();
                }
                
            }
        }
        }
      while(!pq.isEmpty())
      {
          Tweet t = pq.poll();
         // System.out.println( t.TweetId + " "+ t.createdAt);
          result.add(0,t.TweetId); // always add in front (q's last element will be fisrt element in list)
      }
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        
        if(!users.containsKey(followerId))
        {
             users.put(followerId,new HashSet<>());
        }
        users.get(followerId).add(followeeId);
     
    }
    
    public void unfollow(int followerId, int followeeId) {
         if(users.containsKey(followerId))
        {
            users.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */