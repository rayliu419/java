package leetcode;

import leetcode.SumOfLeft;
import leetcode.TreeNode;
import org.junit.Test;

public class SumOfLeftTest {

    @Test
    public void sumOfLeftLeaves() {
        TreeNode root = new TreeNode(3,
                new TreeNode(9, null, null),
                new TreeNode(20,
                        new TreeNode(15, null, null),
                        new TreeNode(7, null, null))
        );

        SumOfLeft sumOfLeft = new SumOfLeft();
        System.out.println(sumOfLeft.sumOfLeftLeaves(root));

    }
}