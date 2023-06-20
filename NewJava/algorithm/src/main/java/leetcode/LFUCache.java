package leetcode;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/lfu-cache/submissions/968006016/
 * 虽然实现了，但是感觉不优雅
 * 使用了TreeMap来做排序，这里主要的问题是，如果用List之类的排序，性能不行
 * 如果要求平摊代价O(1)，基本只能往Map上靠。
 */

public class LFUCache {

    class VisitInfo {

        // 访问次数
        int frequency;

        // 最新访问时间
        int latestVisit;

        public VisitInfo(int frequency, int latestVisit) {
            this.frequency = frequency;
            this.latestVisit = latestVisit;
        }
    }

    int userCounter;

    int curSize;

    int capacity;

    Map<Integer, Integer> cache;

    TreeMap<VisitInfo, Integer> visitInfoIntTreeMap;

    Map<Integer, VisitInfo> link;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        userCounter = 0;
        curSize = 0;
        cache = new HashMap<>();
        visitInfoIntTreeMap = new TreeMap<>(new Comparator<VisitInfo>() {
            @Override
            public int compare(VisitInfo o1, VisitInfo o2) {
                if (o1.frequency != o2.frequency) {
                    return o2.frequency - o1.frequency;
                } else {
                    return o2.latestVisit - o1.latestVisit;
                }
            }
        });
        link = new HashMap<>();
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            VisitInfo old = link.get(key);
            userCounter++;
            // 更新淘汰信息
            VisitInfo newVisitInfo = new VisitInfo(old.frequency + 1, userCounter);
            // 需要remove在重新put吗？还是可以直接修改，自动重新排序？
            visitInfoIntTreeMap.remove(old, key);
            visitInfoIntTreeMap.put(newVisitInfo, key);
            // link信息也要更新
            old.frequency = newVisitInfo.frequency;
            old.latestVisit = newVisitInfo.latestVisit;
            // 返回结果
            return cache.get(key);
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        userCounter++;
        if (cache.containsKey(key)) {
            // cache更新新值
            cache.put(key, value);

            // link信息也要更新
            VisitInfo old = link.get(key);
            VisitInfo newVisitInfo = new VisitInfo(old.frequency + 1, userCounter);
            // 需要remove在重新put吗？还是可以直接修改，自动重新排序？
            visitInfoIntTreeMap.remove(old, key);
            visitInfoIntTreeMap.put(newVisitInfo, key);
            // link信息也要更新
            old.frequency = newVisitInfo.frequency;
            old.latestVisit = newVisitInfo.latestVisit;
            return;
        }
        if (curSize < capacity) {
            cache.put(key, value);
            VisitInfo visitInfo = new VisitInfo(1, userCounter);
            link.put(key, visitInfo);
            visitInfoIntTreeMap.put(visitInfo, key);
            curSize++;
        } else {
            // 需要淘汰
            Map.Entry<VisitInfo, Integer> lastEntry = visitInfoIntTreeMap.lastEntry();
            visitInfoIntTreeMap.remove(lastEntry.getKey(), lastEntry.getValue());
            cache.remove(lastEntry.getValue());
            link.remove(lastEntry.getValue());

            VisitInfo visitInfo = new VisitInfo(1, userCounter);
            visitInfoIntTreeMap.put(visitInfo, key);
            cache.put(key, value);
            link.put(key, visitInfo);
        }
    }

    public static void main(String args[]) {
        LFUCache lfu = new LFUCache(2);
        lfu.put(1, 1);   // cache=[1,_], cnt(1)=1
        lfu.put(2, 2);   // cache=[2,1], cnt(2)=1, cnt(1)=1
        System.out.println(lfu.get(1));      // return 1
        // cache=[1,2], cnt(2)=1, cnt(1)=2
        lfu.put(3, 3);   // 2 is the LFU key because cnt(2)=1 is the smallest, invalidate 2.
        // cache=[3,1], cnt(3)=1, cnt(1)=2
        System.out.println(lfu.get(2)); // return -1 (not found)
        System.out.println(lfu.get(3)); // return 3
//        lfu.get(2);      // return -1 (not found)
//        lfu.get(3);      // return 3
        // cache=[3,1], cnt(3)=2, cnt(1)=2
        lfu.put(4, 4);   // Both 1 and 3 have the same cnt, but 1 is LRU, invalidate 1.
        // cache=[4,3], cnt(4)=1, cnt(3)=2
        System.out.println(lfu.get(1));
        System.out.println(lfu.get(3));
//        lfu.get(1);      // return -1 (not found)
//        lfu.get(3);      // return 3
        // cache=[3,4], cnt(4)=1, cnt(3)=3
        System.out.println(lfu.get(4));
        lfu.get(4);      // return 4
        // [1, -1, 3, -1, 3, 4]
        System.out.println("end");
    }
}
