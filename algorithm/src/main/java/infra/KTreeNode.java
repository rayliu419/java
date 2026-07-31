package infra;

import java.util.ArrayList;
import java.util.List;

public class KTreeNode {

    public int val;
    public List<KTreeNode> children;

    public KTreeNode(int val) {
        this.val = val;
        this.children = new ArrayList<>();
    }

    public KTreeNode(int val, List<KTreeNode> children) {
        this.val = val;
        this.children = children;
    }
}
