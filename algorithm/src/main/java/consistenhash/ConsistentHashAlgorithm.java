package consistenhash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 简单的模拟一致性hash算法的实现。
 * key -> partition(virtual node) -> node(实际物理节点)
 *
 * 机器代表的vnode - 每个机器生成1000代表的vnode点(怎么决定分配给某个机器哪1000个数?)机器与vnode的mapping信息要被记录下来
 * 数据hash到某个vnode点，数据存放的节点是数据hash的点顺时针找到的第一个vnode代表的机器。怎么找到第一个顺时针找到的vnode节点(TreeMap数据结构支持)
 *
 * re-balance的问题：
 * 1. 新增加机器的时候，数据迁移就是新的vnode节点向顺时针的节点要数据，保证了均摊迁移代价。
 * 2. 对于cache系统，如果在这个期间有客户发请求，应该不会影响，就是在cache上读不到数据而已。
 * 3. 对于关系数据库分表或者其他类似DynamoDB等，在扩张节点的时候如果有用户发读/写请求，应该怎么解决这个问题？- 例如读请求就直接读不到了？或者写的时候发现要merge两个版本。
 *
 * Simple implementation of consistent hashing algorithm.
 * key -> partition(virtual node) -> node(actual physical node)
 *
 * vnode for each machine - each machine generates 1000 vnode points. The mapping between machine and vnode is recorded.
 * Data is hashed to a vnode, and the storage node is the first machine representing the vnode found clockwise from the hash point.
 *
 * Re-balance issues:
 * 1. When adding machines, data migration only happens from the new vnode to the clockwise node, ensuring balanced migration cost.
 * 2. For cache systems, client requests during this period are not affected - data is simply not found in cache.
 * 3. For sharded relational databases or systems like DynamoDB, how to handle read/write requests during expansion?
 *
 * @param <T> the type of node in the hash ring
 */
public class ConsistentHashAlgorithm<T> {

    // 物理节点对应的虚拟节点个数，有可能后面的被前面的覆盖了，所以每个物理节点对应的<=1000个。
    private final int PHYSICAL_TO_VIRTUAL_MAPPING_NUMBER = 1000;

    // 用来存储虚拟节点hash值到真实node的映射
    private final SortedMap<Long, T> circle = new TreeMap();

    /**
     * MD5生成的hash函数，基本保证每个字符串只有一个对应的long。
     * MD5 hash function that ensures each string maps to a unique long value.
     *
     * @param key the key string to be hashed / 要进行哈希的键字符串
     * @return the hash value as long / 哈希后的长整型值
     */
    public long getVirtualIndex(String key) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.reset();
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();
        long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8)
                | (long) (bKey[0] & 0xFF);
        return res & 0xffffffffL;
    }

    /**
     * 如果某个hash值发生碰撞怎么办？- TreeMap 会保存后面的那个key/value。
     * What happens if hash collision occurs? - TreeMap keeps the latter key/value pair.
     * 向哈希环添加节点，为每个物理节点生成1000个虚拟节点。
     * Adds a node to the hash ring, generating 1000 virtual nodes for each physical node.
     *
     * @param node the node to add to the hash ring / 要添加到哈希环的节点
     */
    public void add(T node) throws NoSuchAlgorithmException {
        for (int i = 0; i < PHYSICAL_TO_VIRTUAL_MAPPING_NUMBER; i++) {
            // 为每个节点生成PHYSICAL_TO_VIRTUAL_MAPPING_NUMBER个虚拟节点，并把对应关系存放起来。
            circle.put(getVirtualIndex(node.toString() + i), node);
        }
    }

    /**
     * 删除节点和节点的映射关系
     * Removes a node and its mapping relationship from the hash ring.
     * 如果有hash碰撞的情况，摘取的对应关系可能有误。我觉得在删除前需要比较返回的node是不是当前的node。
     * If hash collision exists, the removed mapping might be incorrect. Should verify the returned node is the current node before removal.
     *
     * @param node the node to remove from the hash ring / 要从哈希环中移除的节点
     */
    public void remove(T node) throws NoSuchAlgorithmException {
        for (int i = 0; i < PHYSICAL_TO_VIRTUAL_MAPPING_NUMBER; i++) {
            // 如果有hash碰撞的情况，摘取的对应关系可能有误。我觉得在删除前需要比较返回的node是不是当前的node。
            circle.remove(getVirtualIndex(node.toString() + i));
        }
    }

    /**
     * 获得一个最近的顺时针节点
     * Gets the nearest node in clockwise direction for the given key.
     * 为给定键取Hash，取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * Hashes the given key and retrieves the physical node corresponding to the nearest virtual node in clockwise direction.
     *
     * @param key the key to locate / 要查找的键
     * @return the nearest clockwise node, or null if hash ring is empty / 顺时针方向最近的节点，如果哈希环为空则返回null
     */
    public T get(Object key) throws NoSuchAlgorithmException {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = getVirtualIndex(key.toString());
        if (!circle.containsKey(hash)) {
            // 返回大于hash的值的map部分，如果剩下的map是空，则映射到第一个值
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}
