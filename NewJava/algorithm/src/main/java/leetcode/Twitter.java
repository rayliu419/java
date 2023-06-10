package leetcode;

import java.util.*;

// https://leetcode.com/problems/design-twitter/
// java heap数据结构用什么? - PriorityQueue

public class Twitter {

    public class Pair {
        int tweetId;
        int pubTime;

        public Pair(int x, int y) {
            tweetId = x;
            pubTime = y;
        }
    }

    private HashMap<Integer, Stack<Pair>> userIdToTweets;

    // 用户follow了哪些人
    private HashMap<Integer, Set<Integer>> userFollows;

    int time = 0;

    public Twitter() {
        userIdToTweets = new HashMap<>();
        userFollows = new HashMap<>();
    }

    public void postTweet(int userId, int tweetId) {
        Stack<Pair> tweets = userIdToTweets.getOrDefault(Integer.valueOf(userId), new Stack<Pair>());
        Pair newPair = new Pair(tweetId, time);
        tweets.add(newPair);
        userIdToTweets.put(userId, tweets);
        time += 1;
    }

    public void follow(int followerId, int followeeId) {
        Set<Integer> l = userFollows.getOrDefault(followerId, new HashSet<>());
        l.add(followeeId);
        userFollows.putIfAbsent(followerId, l);
    }

    public void unfollow(int followerId, int followeeId) {
        Set<Integer> s = userFollows.get(followerId);
        if (s != null) {
            s.remove(followeeId);
        }
    }

    public List<Integer> getNewsFeed(int userId) {
        ArrayList<Pair> unOrderedTweets = new ArrayList<>();
        if (userIdToTweets.get(userId) != null) {
            // 自己发的tweets
            unOrderedTweets.addAll(userIdToTweets.get(userId));
        }

        Set<Integer> curUserFollows = userFollows.get(userId);
        if (curUserFollows != null) {
            // 计算自己follow的tweets
            for (Integer i : curUserFollows) {
                if (userIdToTweets.get(i) != null) {
//                    scanStacks.add(userIdToTweets.get(i));
                    unOrderedTweets.addAll(userIdToTweets.get(i));
                }
            }
        }
        // 按时间排序
        PriorityQueue<Pair> recentTweets = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return o2.pubTime - o1.pubTime;
            }
        });
        recentTweets.addAll(unOrderedTweets);
        ArrayList<Integer> result = new ArrayList<>();
        int count = 0;
        while (count < 10 && !recentTweets.isEmpty()) {
            result.add(recentTweets.poll().tweetId);
            count++;
        }
        // TODO: 优化，k个有序队列求topK问题
        return result;
    }

    public static void main(String args[]) {
        Twitter obj = new Twitter();
        obj.postTweet(1, 5);
        List<Integer> tweets1 = obj.getNewsFeed(1);
        obj.follow(1, 2);
        obj.postTweet(2, 6);
        List<Integer> tweets2 = obj.getNewsFeed(1);
        obj.unfollow(1, 2);
        List<Integer> tweets3 = obj.getNewsFeed(1);
        System.out.println("end");
    }

}
