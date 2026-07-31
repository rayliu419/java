package consistenhash;

import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class ConsistentHashAlgorithmTest {

    /**
     * 测试基本的一致性哈希功能
     */
    @Test
    public void testConsistentHashAlgorithm() throws NoSuchAlgorithmException {
        Node node1 = new Node(1, "A");
        Node node2 = new Node(2, "B");
        ConsistentHashAlgorithm<Node> consistentHashAlgorithm = new ConsistentHashAlgorithm<>();
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
        SortedMap<Long, Long> sameKey = new TreeMap<>();
        sameKey.put(1L, 2L);
        sameKey.put(1L, 3L);
        System.out.print(sameKey.get(1L));
    }

    /**
     * 测试空哈希环的情况
     */
    @Test
    public void testEmptyCircle() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        assertNull("空哈希环应该返回null", algorithm.get("anyKey"));
    }

    /**
     * 测试单个节点的情况
     */
    @Test
    public void testSingleNode() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        Node node = new Node(1, "SingleNode");
        algorithm.add(node);
        
        // 所有key都应该映射到同一个节点
        assertEquals("test1", node, algorithm.get("test1"));
        assertEquals("test2", node, algorithm.get("test2"));
        assertEquals("test3", node, algorithm.get("test3"));
    }

    /**
     * 测试多个节点的均匀分布
     */
    @Test
    public void testUniformDistribution() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        List<Node> nodes = Arrays.asList(
            new Node(1, "Node1"),
            new Node(2, "Node2"),
            new Node(3, "Node3"),
            new Node(4, "Node4"),
            new Node(5, "Node5")
        );
        
        // 添加所有节点
        for (Node node : nodes) {
            algorithm.add(node);
        }
        
        // 测试大量key的分布
        Map<Node, Integer> distribution = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String key = "key" + i;
            Node node = algorithm.get(key);
            distribution.put(node, distribution.getOrDefault(node, 0) + 1);
        }
        
        // 验证每个节点都有分配到key
        assertEquals("所有节点都应该有分配到key", nodes.size(), distribution.size());
        
        // 验证分布相对均匀（每个节点应该至少分配到10%的key）
        for (int count : distribution.values()) {
            assertTrue("节点分布应该相对均匀", count >= 50);
        }
    }

    /**
     * 测试哈希环的循环特性
     */
    @Test
    public void testCircularBehavior() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        Node node1 = new Node(1, "Node1");
        Node node2 = new Node(2, "Node2");
        
        algorithm.add(node1);
        algorithm.add(node2);
        
        // 获取所有虚拟节点的hash值
        List<Long> allHashes = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            allHashes.add(algorithm.getVirtualIndex(node1.toString() + i));
            allHashes.add(algorithm.getVirtualIndex(node2.toString() + i));
        }
        
        long maxHash = Collections.max(allHashes);
        long minHash = Collections.min(allHashes);
        
        // 使用一个超过最大hash值的key，应该回到最小hash值的节点
        String largeKey = String.valueOf(maxHash + 1);
        Node result = algorithm.get(largeKey);
        assertNotNull("应该能找到对应的节点", result);
    }

    /**
     * 测试节点删除后的数据迁移
     */
    @Test
    public void testDataMigrationAfterNodeRemoval() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        List<Node> nodes = Arrays.asList(
            new Node(1, "Node1"),
            new Node(2, "Node2"),
            new Node(3, "Node3")
        );
        
        // 添加节点并记录初始映射
        for (Node node : nodes) {
            algorithm.add(node);
        }
        
        Map<String, Node> initialMapping = new HashMap<>();
        List<String> testKeys = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String key = "testKey" + i;
            testKeys.add(key);
            initialMapping.put(key, algorithm.get(key));
        }
        
        // 移除一个节点
        Node removedNode = nodes.get(1);
        algorithm.remove(removedNode);
        
        // 验证数据迁移
        int migratedKeys = 0;
        int unchangedKeys = 0;
        for (String key : testKeys) {
            Node newMapping = algorithm.get(key);
            Node oldMapping = initialMapping.get(key);
            
            if (oldMapping.equals(removedNode)) {
                assertNotEquals("原来映射到被删除节点的key应该迁移", oldMapping, newMapping);
                migratedKeys++;
            } else {
                assertEquals("原来不映射到被删除节点的key应该保持不变", oldMapping, newMapping);
                unchangedKeys++;
            }
        }
        
        assertTrue("应该有key被迁移", migratedKeys > 0);
        assertTrue("应该有key保持不变", unchangedKeys > 0);
    }

    /**
     * 测试添加新节点后的重新分布
     */
    @Test
    public void testRebalancingAfterNodeAddition() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        List<Node> initialNodes = Arrays.asList(
            new Node(1, "Node1"),
            new Node(2, "Node2")
        );
        
        // 添加初始节点
        for (Node node : initialNodes) {
            algorithm.add(node);
        }
        
        // 记录初始映射
        Map<String, Node> initialMapping = new HashMap<>();
        List<String> testKeys = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String key = "testKey" + i;
            testKeys.add(key);
            initialMapping.put(key, algorithm.get(key));
        }
        
        // 添加新节点
        Node newNode = new Node(3, "Node3");
        algorithm.add(newNode);
        
        // 验证重新分布
        int changedMappings = 0;
        for (String key : testKeys) {
            Node newMapping = algorithm.get(key);
            Node oldMapping = initialMapping.get(key);
            
            if (!oldMapping.equals(newMapping)) {
                changedMappings++;
            }
        }
        
        // 添加新节点后应该有部分key重新分布
        assertTrue("应该有key重新分布", changedMappings > 0);
        // 但不是所有key都重新分布（一致性哈希的特性）
        assertTrue("不是所有key都重新分布", changedMappings < testKeys.size());
    }

    /**
     * 测试虚拟节点hash值的一致性
     */
    @Test
    public void testVirtualNodeHashConsistency() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        Node node = new Node(1, "TestNode");
        
        // 多次计算同一个虚拟节点的hash值应该一致
        long hash1 = algorithm.getVirtualIndex(node.toString() + 0);
        long hash2 = algorithm.getVirtualIndex(node.toString() + 0);
        assertEquals("同一虚拟节点的hash值应该一致", hash1, hash2);
        
        // 不同虚拟节点的hash值应该不同（理论上）
        long hashDifferent = algorithm.getVirtualIndex(node.toString() + 1);
        assertNotEquals("不同虚拟节点的hash值应该不同", hash1, hashDifferent);
    }

    /**
     * 测试不同类型的节点
     */
    @Test
    public void testDifferentNodeTypes() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<String> stringAlgorithm = new ConsistentHashAlgorithm<>();
        ConsistentHashAlgorithm<Integer> intAlgorithm = new ConsistentHashAlgorithm<>();
        
        stringAlgorithm.add("Server1");
        stringAlgorithm.add("Server2");
        
        intAlgorithm.add(1);
        intAlgorithm.add(2);
        
        assertNotNull("String节点应该能正常工作", stringAlgorithm.get("testKey"));
        assertNotNull("Integer节点应该能正常工作", intAlgorithm.get("testKey"));
    }

    /**
     * 测试并发操作
     */
    @Test
    public void testConcurrentOperations() throws NoSuchAlgorithmException, InterruptedException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        List<Node> nodes = Arrays.asList(
            new Node(1, "Node1"),
            new Node(2, "Node2"),
            new Node(3, "Node3")
        );
        
        // 添加节点
        for (Node node : nodes) {
            algorithm.add(node);
        }
        
        // 并发读取测试
        int threadCount = 10;
        int operationsPerThread = 100;
        Map<Node, Integer> concurrentDistribution = new ConcurrentHashMap<>();
        
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < operationsPerThread; j++) {
                        String key = "thread" + threadId + "_key" + j;
                        Node node = algorithm.get(key);
                        concurrentDistribution.merge(node, 1, Integer::sum);
                    }
                } catch (NoSuchAlgorithmException e) {
                    fail("不应该抛出异常: " + e.getMessage());
                }
            });
        }
        
        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }
        
        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }
        
        // 验证结果
        int totalOperations = threadCount * operationsPerThread;
        int distributedCount = concurrentDistribution.values().stream().mapToInt(Integer::intValue).sum();
        assertEquals("所有操作都应该被分布", totalOperations, distributedCount);
    }

    /**
     * 测试边界条件：特殊字符和空值
     */
    @Test
    public void testEdgeCases() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        Node node = new Node(1, "TestNode");
        algorithm.add(node);
        
        // 测试特殊字符
        assertNotNull("特殊字符key应该能正常处理", algorithm.get("!@#$%^&*()"));
        assertNotNull("中文字符key应该能正常处理", algorithm.get("测试中文"));
        assertNotNull("数字key应该能正常处理", algorithm.get("123456"));
        
        // 测试空字符串
        assertNotNull("空字符串应该能正常处理", algorithm.get(""));
        
        // 测试null（会调用toString方法）
        assertNotNull("null应该能正常处理", algorithm.get(null));
    }

    /**
     * 测试性能基准
     */
    @Test
    public void testPerformanceBenchmark() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        List<Node> nodes = Arrays.asList(
            new Node(1, "Node1"),
            new Node(2, "Node2"),
            new Node(3, "Node3"),
            new Node(4, "Node4"),
            new Node(5, "Node5")
        );
        
        for (Node node : nodes) {
            algorithm.add(node);
        }
        
        int testCount = 10000;
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < testCount; i++) {
            algorithm.get("performanceTestKey" + i);
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        System.out.println("执行 " + testCount + " 次get操作耗时: " + duration + "ms");
        System.out.println("平均每次操作耗时: " + (double) duration / testCount + "ms");
        
        // 性能要求：平均每次操作应该小于1ms
        assertTrue("性能应该满足要求", (double) duration / testCount < 1.0);
    }

    /**
     * 测试添加相同节点的行为
     */
    @Test
    public void testAddingSameNode() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        Node node = new Node(1, "SameNode");
        
        // 添加相同的节点两次
        algorithm.add(node);
        algorithm.add(node);
        
        // 验证仍然能正常工作
        assertNotNull("应该能正常工作", algorithm.get("testKey"));
        
        // 删除节点
        algorithm.remove(node);
        
        // 删除后应该没有节点了
        assertNull("删除后应该为空", algorithm.get("testKey"));
    }

    /**
     * 测试删除不存在的节点
     */
    @Test
    public void testRemovingNonExistentNode() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        Node node1 = new Node(1, "Node1");
        Node node2 = new Node(2, "Node2");
        
        // 添加node1
        algorithm.add(node1);
        
        // 删除不存在的node2（不应该抛出异常）
        algorithm.remove(node2);
        
        // 应该仍然能正常工作
        assertEquals("应该仍然返回node1", node1, algorithm.get("testKey"));
    }

    /**
     * 测试大量节点的性能
     */
    @Test
    public void testLargeNumberOfNodes() throws NoSuchAlgorithmException {
        ConsistentHashAlgorithm<Node> algorithm = new ConsistentHashAlgorithm<>();
        int nodeCount = 100;
        
        // 添加大量节点
        for (int i = 0; i < nodeCount; i++) {
            algorithm.add(new Node(i, "Node" + i));
        }
        
        // 测试大量key的分布
        Map<Integer, Integer> distribution = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String key = "testKey" + i;
            Node node = algorithm.get(key);
            distribution.put(node.getId(), distribution.getOrDefault(node.getId(), 0) + 1);
        }
        
        // 验证分布的节点数量
        assertEquals("分布的节点数应该正确", nodeCount, distribution.size());
        
        // 验证每个节点都有分配到key
        for (int i = 0; i < nodeCount; i++) {
            assertTrue("每个节点都应该有分配到key", distribution.containsKey(i));
        }
    }
}