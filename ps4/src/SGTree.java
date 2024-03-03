/**
 * ScapeGoat Tree class
 *
 * This class contains some of the basic code for implementing a ScapeGoat tree.
 * This version does not include any of the functionality for choosing which node
 * to scapegoat.  It includes only code for inserting a node, and the code for rebuilding
 * a subtree.
 */

public class SGTree {

    // Designates which child in a binary tree
    enum Child {LEFT, RIGHT}

    /**
     * TreeNode class.
     *
     * This class holds the data for a node in a binary tree.
     *
     * Note: we have made things public here to facilitate problem set grading/testing.
     * In general, making everything public like this is a bad idea!
     *
     */
    public static class TreeNode {
        int key;
        public TreeNode left = null;
        public TreeNode right = null;

        TreeNode(int k) {
            key = k;
        }

        private String formatTree(String prefix) {
            String s = String.format("%s%d", prefix, this.key);
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
     * Counts the number of nodes in the specified subtree
     *
     * @param node  the parent node, not to be counted
     * @param child the specified subtree
     * @return number of nodes
     */
    public int countNodes(TreeNode node, Child child) {
        node = child == Child.LEFT ? node.left : node.right;
        return node == null ? 0 : countNodes(node, Child.LEFT) + 1 + countNodes(node, Child.RIGHT);
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
     * Builds an array of nodes in the specified subtree
     *
     * @param node  the parent node, not to be included in returned array
     * @param child the specified subtree
     * @return array of nodes
     */
    public TreeNode[] enumerateNodes(TreeNode node, Child child) {
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
        node.left = start < mid ? buildTree(nodeList, start, mid - 1) : null;
        node.right = mid < end ? buildTree(nodeList, mid + 1, end) : null;
        return node;
    }

    /**
     * Builds a tree from the list of nodes
     * Returns the node that is the new root of the subtree
     *
     * @param nodeList ordered array of nodes
     * @return the new root node
     */
    public TreeNode buildTree(TreeNode[] nodeList) {
        return nodeList.length == 0 ? null : buildTree(nodeList, 0, nodeList.length - 1);
    }

    /**
    * Rebuilds the specified subtree of a node
    * 
    * @param node the part of the subtree to rebuild
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
    * Inserts a key into the tree
    *
    * @param key the key to insert
    */
    public void insert(int key) {
        if (root == null) {
            root = new TreeNode(key);
            return;
        }

        TreeNode node = root;

        while (true) {
            if (key <= node.key) {
                if (node.left == null) break;
                node = node.left;
            } else {
                if (node.right == null) break;
                node = node.right;
            }
        }

        if (key <= node.key) {
            node.left = new TreeNode(key);
        } else {
            node.right = new TreeNode(key);
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
        tree.rebuild(tree.root, Child.RIGHT);
        System.out.println("the final tree is");
        System.out.println(tree);
    }
}
