package main.leetcode;

import javax.swing.*;
import java.util.*;

// https://leetcode.com/problems/design-twitter/
// java heap数据结构用什么?

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

    private HashMap<Integer, List<Integer>> userFollows;

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

    /** Retrieve the 10 most recent tweet ids in the user's news feed.
     *  Each item in the news feed must be posted by users who the user followed or by the user herself.
     *  Tweets must be ordered from most recent to least recent. */
    public List<Integer> getNewsFeed(int userId) {
        ArrayList<Stack<Pair>> scanStacks = new ArrayList<>();
        if (userIdToTweets.get(userId) != null) {
            // 自己发的tweets
            scanStacks.add(userIdToTweets.get(userId));
        }

        List<Integer> curUserFollows = userFollows.get(userId);
        if (curUserFollows != null) {
            // 计算自己follow的tweets
            for (Integer i : curUserFollows) {
                if (userIdToTweets.get(i) != null) {
                    scanStacks.add(userIdToTweets.get(i));
                }
            }
        }
        // 按时间排序
        return new ArrayList<>();
    }

    /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
    public void follow(int followerId, int followeeId) {
        List<Integer> l = userFollows.getOrDefault(followeeId, new ArrayList<>());
        l.add(followerId);
        userFollows.putIfAbsent(followeeId, l);
    }

}
