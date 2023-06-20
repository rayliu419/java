package leetcode;

public class TreeSerializer {

    class Counter {

        int index;

        public Counter() {
            index = 0;
        }
    }

    private static final String DELIMITER = ",";

    private static final String EMPTY = "#";

    public String preOrderSerialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        preOrderSerializeHelp(sb, root);
        return sb.toString();
    }

    private void preOrderSerializeHelp(StringBuilder sb, TreeNode node) {
        if (node == null) {
            sb.append(EMPTY);
            return;
        }
        sb.append(node.val);
        sb.append(DELIMITER);
        preOrderSerializeHelp(sb, node.left);
        sb.append(DELIMITER);
        preOrderSerializeHelp(sb, node.right);
    }

    public TreeNode preOrderDeserialize(String s) {
        String[] elements = s.split(DELIMITER);
        if (elements.length == 0) {
            return null;
        }
        Counter counter = new Counter();
        return preOrderDeserializeHelp(counter, elements);
    }

    /**
     * 反序列化的核心是：序列化的时候，当前序列化字符串的第一个元素和第两个元素肯定是当前的左孩子和右孩子，只要当前的元素不为#。
     * 如果当前为#，则说明以当前为根的树是空子树，直接返回
     *
     * @param counter
     * @param s
     * @return
     */
    private TreeNode preOrderDeserializeHelp(Counter counter, String[] s) {
        if (counter.index >= s.length) {
            return null;
        }
        if (s[counter.index].equals("#")) {
            counter.index++;
            return null;
        }
        TreeNode node = new TreeNode(Integer.valueOf(s[counter.index]));
        counter.index++;
        node.left = preOrderDeserializeHelp(counter, s);
//        counter.index++; 易错，实际上在递归调用中已经加了
        node.right = preOrderDeserializeHelp(counter, s);
//        counter.index++;
        return node;
    }

    public static void main(String[] args) {
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

        // TODO: 层次遍历？
        // [30 10 20 50 # 45 35 # # # # # #]
    }
}
