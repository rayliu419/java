package consistenhash;

import org.junit.Test;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.junit.Assert.assertNotEquals;

public class ConsistentHashAlgorithmTest {

    /**
     *
     */
    @Test
    public void testConsistentHashAlgorithm() {
        Node node1 = new Node(1, "A");
        Node node2 = new Node(2, "B");
        ConsistentHashAlgorithm<Node> consistentHashAlgorithm = new ConsistentHashAlgorithm();
        // 为物理节点生成虚拟节点
        consistentHashAlgorithm.add(node1);
        consistentHashAlgorithm.add(node2);

        // 获取当前键值落到的物理节点上。
        String testKey = "testKey";
        Node retNode = consistentHashAlgorithm.get(testKey);
        System.out.println(retNode.toString());

        // 删除落到的物理节点，再次查询键值落到的物理节点，肯定不是前一个节点了。
        consistentHashAlgorithm.remove(retNode);
        Node retNode2 = consistentHashAlgorithm.get(testKey);
        System.out.println(retNode2.toString());

        assertNotEquals(retNode.toString(), retNode2.toString());
    }

    /**
     * 验证TreeMap的重复键的问题。
     */
    @Test
    public void testTreeMap() {
        SortedMap<Long, Long> sameKey = new TreeMap();
        sameKey.put(1L, 2L);
        sameKey.put(1L, 3L);
        System.out.print(sameKey.get(1L));
    }
}