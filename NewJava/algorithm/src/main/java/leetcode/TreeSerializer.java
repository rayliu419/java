package leetcode;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * 树的序列化
 * 有几种不同的序列化方式：
 *     前序树序列化
 *     后序序列化
 *     层次序列化
 *     中序序列化是不行的，因为中序对于两种树的序列化一样，反序列化时区分不了。
 *
 */
public class TreeSerializer {

    /**
     * 前序序列化递归中用来记录当前用到了哪
     */
    class Counter {
        int index;
        public Counter() {
            index = 0;
        }
    }

    private static final String SEP = ",";

    private static final String EMPTY = "#";

    public String preOrderSerialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        doPreOrderSerialize(sb, root);
        return sb.toString();
    }

    private void doPreOrderSerialize(StringBuilder sb, TreeNode node) {
        if (node == null) {
            sb.append(EMPTY);
            return;
        }
        sb.append(node.val);
        sb.append(SEP);
        doPreOrderSerialize(sb, node.left);
        sb.append(SEP);
        doPreOrderSerialize(sb, node.right);
    }

    public TreeNode preOrderDeserialize(String s) {
        String[] elements = s.split(SEP);
        if (elements.length == 0) {
            return null;
        }
        Counter counter = new Counter();
        return doPreOrderDeserialize(counter, elements);
    }

    /**
     * 反序列化的核心是：序列化的时候，当前序列化字符串的第一个元素和第两个元素肯定是当前的左孩子和右孩子，只要当前的元素不为#。
     * 如果当前为#，则说明以当前为根的树是空子树，直接返回
     *
     * @param counter
     * @param s
     * @return
     */
    private TreeNode doPreOrderDeserialize(Counter counter, String[] s) {
        if (counter.index >= s.length) {
            return null;
        }
        if (s[counter.index].equals("#")) {
            counter.index++;
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(s[counter.index]));
        counter.index++;
        node.left = doPreOrderDeserialize(counter, s);
        node.right = doPreOrderDeserialize(counter, s);
        return node;
    }

    public static void testPreOrderSerialize() {
        TreeSerializer treeSerializer = new TreeSerializer();
//                  30
//                /     \
//              10      20
//            /        /  \
//          50        45    35
        TreeNode treeNode50 = new TreeNode(50, null, null);
        TreeNode treeNode45 = new TreeNode(45, null, null);
        TreeNode treeNode35 = new TreeNode(35, null, null);
        TreeNode treeNode10 = new TreeNode(10, treeNode50, null);
        TreeNode treeNode20 = new TreeNode(20, treeNode45, treeNode35);
        TreeNode treeNode30 = new TreeNode(30, treeNode10, treeNode20);
        String result = treeSerializer.preOrderSerialize(treeNode30);
        System.out.println(result);
        // [30 10 50 # # # 20 45 # # 35 # #]

        TreeNode root = treeSerializer.preOrderDeserialize(result);
        System.out.println(root.val);
    }


    /**
     *            1
     *         2    3
     *      4
     *
     *  [1, 2, 3, 4, #, #, #] 当前我的方式
     *
     * @param node
     * @return
     */
    public String levelSerialize(TreeNode node) {
        if (node == null) {
            return "";
        }
        Queue<TreeNode> curLayer = new ArrayDeque<>();
        Queue<TreeNode> nextLayer = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        boolean nextLayerAllEmpty = false;
        TreeNode PLACE_HOLDER = new TreeNode(Integer.MAX_VALUE, null, null);
        curLayer.add(node);
        while (true) {
            while (!curLayer.isEmpty()) {
                TreeNode cur = curLayer.poll();
                if (cur == PLACE_HOLDER) {
                    sb.append(EMPTY).append(SEP);
                    continue;
                }
                sb.append(cur.val).append(",");
                if (cur.left == null) {
                    nextLayer.add(PLACE_HOLDER);
                } else {
                    nextLayer.add(cur.left);
                    nextLayerAllEmpty = false;
                }
                if (cur.right == null) {
                    nextLayer.add(PLACE_HOLDER);
                } else {
                    nextLayer.add(cur.right);
                    nextLayerAllEmpty = false;
                }
            }
            if (nextLayerAllEmpty) {
                break;
            }
            curLayer.addAll(nextLayer);
            nextLayer.clear();
            nextLayerAllEmpty = true;
        }
        sb.setLength(sb.toString().length() - 1);
        return sb.toString();
    }

    public TreeNode levelDeserialize(String data) {
        if (data.length() == 0) {
            return null;
        }
        String[] split = data.split(SEP);
        Map<Integer, TreeNode> cache = new HashMap<>();
        TreeNode root = new TreeNode(Integer.parseInt(split[0]), null, null);
        int globalIndex = 0;
        cache.put(0, root);
        for (int i = 0; i < split.length; i++) {
            if (split[i].equals(EMPTY)) {
                continue;
            }
            TreeNode cur = cache.get(i);
            globalIndex++;
            if (globalIndex < split.length && !split[globalIndex].equals(EMPTY)) {
                TreeNode left = new TreeNode(Integer.parseInt(split[globalIndex]), null, null);
                cur.left = left;
                cache.put(globalIndex, left);
            }
            globalIndex++;
            if (globalIndex < split.length && !split[globalIndex].equals(EMPTY)) {
                TreeNode right = new TreeNode(Integer.parseInt(split[globalIndex]), null, null);
                cur.right = right;
                cache.put(globalIndex, right);
            }
        }
        return root;
    }


    public static void main(String[] args) {

    }
}
