package binarytree;

import org.example.binarytree.BinaryTree;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BinaryTreeTest {

    private final BinaryTree binaryTree = new BinaryTree();

    @Before
    public void initTree(){
        binaryTree.addNode(15);
        binaryTree.addNode(25);
        binaryTree.addNode(27);
        binaryTree.addNode(22);
        binaryTree.addNode(17);
        binaryTree.addNode(10);
        binaryTree.addNode(13);
        binaryTree.addNode(7);
        binaryTree.addNode(5);
        binaryTree.addNode(9);
    }


    @Test
    public void addNode(){
        binaryTree.addNode(10);
    }

    @Test
    public void deleteNode(){
        binaryTree.deleteNode(25);
    }


    @Test
    public void getNode(){
        var node = binaryTree.getNode(10);
        Assert.assertEquals(node.getKey(), 10);
    }

    @Test
    public void getNotExistsNode() {
        Assert.assertThrows(RuntimeException.class,
                () -> binaryTree.getNode(111));
    }

    @Test
    public void dfs() {
      binaryTree.dfs();
    }

    @Test
    public void dfsInOrder() {
        binaryTree.dfsInOrder();
    }

    @Test
    public void dfsPostOrder() {
        binaryTree.dfsPostOrder();
    }

    @Test
    public void bfs() {
        binaryTree.bfs();
    }
}
