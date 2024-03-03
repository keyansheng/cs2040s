/**
 * ScapeGoat Tree class
 * <p>
 * This class contains some basic code for implementing a ScapeGoat tree. This version does not include any of the
 * functionality for choosing which node to scapegoat. It includes only code for inserting a node, and the code for
 * rebuilding a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     * <p>
     * This class holds the data for a node in a binary tree.
     * <p>
     * Note: we have made things public here to facilitate problem set grading/testing. In general, making everything
     * public like this is a bad idea!
     */
    public static class TreeNode {
        int key;
        int weight = 1;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }

        private String formatTree(String prefix) {
            String s = String.format("%s%d (weight: %d)", prefix, this.key, this.weight);
            if (this.left != null) {
                s = this.left.formatTree(prefix + "-") + "\n" + s;
            }
            if (this.right != null) {
                s += "\n" + this.right.formatTree(prefix + "-");
            }
            return s;
        }

        public String formatTree() {
            return this.formatTree("");
        }

        @Override
        public String toString() {
            return String.valueOf(this.key);
        }
    }

    // Root of the binary tree
    public TreeNode root = null;

    /**
     * Counts the number of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        node = child == Child.LEFT ? node.left : node.right;
        return node == null ? 0 : node.weight;
    }

    private int enumerateNodes(TreeNode node, TreeNode[] nodes, int index) {
        if (node.left != null) {
            index = enumerateNodes(node.left, nodes, index);
        }
        nodes[index] = node;
        index++;
        if (node.right != null) {
            index = enumerateNodes(node.right, nodes, index);
        }
        return index;
    }

    /**
     * Builds an array of nodes in the specified subtree.
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    TreeNode[] enumerateNodes(TreeNode node, Child child) {
        if (child == Child.LEFT) {
            if (node.left == null) {
                return new TreeNode[0];
            }
            TreeNode[] nodes = new TreeNode[countNodes(node, child)];
            enumerateNodes(node.left, nodes, 0);
            return nodes;
        } else {
            if (node.right == null) {
                return new TreeNode[0];
            }
            TreeNode[] nodes = new TreeNode[countNodes(node, child)];
            enumerateNodes(node.right, nodes, 0);
            return nodes;
        }
    }

    private TreeNode buildTree(TreeNode[] nodeList, int start, int end) {
        int mid = start + (end - start) / 2;
        TreeNode node = nodeList[mid];
        node.weight = end - start + 1;
        node.left = start < mid ? buildTree(nodeList, start, mid - 1) : null;
        node.right = mid < end ? buildTree(nodeList, mid + 1, end) : null;
        return node;
    }

    /**
     * Builds a tree from the list of nodes Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    TreeNode buildTree(TreeNode[] nodeList) {
        return nodeList.length == 0 ? null : buildTree(nodeList, 0, nodeList.length - 1);
    }

    /**
     * Determines if a node is balanced. If the node is balanced, this should return true. Otherwise, it should return
     * false. A node is unbalanced if either of its children has weight greater than 2/3 of its weight.
     *
     * @param node a node to check balance on
     * @return true if the node is balanced, false otherwise
     */
    public boolean checkBalance(TreeNode node) {
        return node == null
                || (countNodes(node, Child.LEFT) * 3 <= node.weight * 2
                        && countNodes(node, Child.RIGHT) * 3 <= node.weight * 2);
    }

    /**
     * Rebuilds the specified subtree of a node.
     *
     * @param node  the part of the subtree to rebuild
     * @param child specifies which child is the root of the subtree to rebuild
     */
    public void rebuild(TreeNode node, Child child) {
        // Error checking: cannot rebuild null tree
        if (node == null) return;
        // First, retrieve a list of all the nodes of the subtree rooted at child
        TreeNode[] nodeList = enumerateNodes(node, child);
        // Then, build a new subtree from that list
        TreeNode newChild = buildTree(nodeList);
        // Finally, replace the specified child with the new subtree
        if (child == Child.LEFT) {
            node.left = newChild;
        } else if (child == Child.RIGHT) {
            node.right = newChild;
        }
    }

    /**
     * Inserts a key into the tree.
     *
     * @param key the key to insert
     */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;
        TreeNode parentNode = null;
        Child parentNodeChild = null;
        TreeNode parentParentNode = null;
        Child parentParentNodeChild = null;

        TreeNode unbalancedNode = null;
        Child unbalancedNodeChild = null;
        TreeNode rebuildNode = null;
        Child rebuildNodeChild = null;

        while (true) {
            node.weight++;
            if (rebuildNode == null && !checkBalance(parentNode)) {
                unbalancedNode = parentNode;
                unbalancedNodeChild = parentNodeChild;
                rebuildNode = parentParentNode;
                rebuildNodeChild = parentParentNodeChild;
            }
            parentParentNode = parentNode;
            parentParentNodeChild = parentNodeChild;
            parentNode = node;
            if (key <= node.key) {
                parentNodeChild = Child.LEFT;
                if (node.left == null) break;
                node = node.left;
            } else {
                parentNodeChild = Child.RIGHT;
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
        }

        if (rebuildNode != null) {
            System.out.println("unbalanced node detected! rebuilding " + rebuildNodeChild + " of " + rebuildNode);
            System.out.println(this);
            rebuild(rebuildNode, rebuildNodeChild);
            System.out.println("successfully rebuilt! the new tree is");
            System.out.println(this);
        }
    }

    @Override
    public String toString() {
        return root.formatTree();
    }

    // Simple main function for debugging purposes
    public static void main(String[] args) {
        SGTree tree = new SGTree();
        System.out.println("inserting " + 0);
        tree.insert(0);
        for (int i = 10; i >= 1; i--) {
            System.out.println("inserting " + i);
            tree.insert(i);
        }
        System.out.println("the final tree is");
        System.out.println(tree);
        System.out.println(tree.checkBalance(tree.root.right));
    }
}
