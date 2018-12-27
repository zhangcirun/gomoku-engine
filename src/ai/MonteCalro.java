package ai;

import java.util.List;

public class MonteCalro extends Agent{

}

class TreeNode{
    private boolean isLeaf;

    private int[][] chess;

    private double reward;

    private int visitsCount;

    private TreeNode parent;

    private List<TreeNode> children;

    public TreeNode(int[][] chess) {
        this.chess = chess;
    }

    public TreeNode(boolean isLeaf, int[][] chess) {
        this.isLeaf = isLeaf;
        this.chess = chess;
    }

    public TreeNode(boolean isLeaf, int[][] chess, TreeNode parent) {
        this.isLeaf = isLeaf;
        this.chess = chess;
        this.parent = parent;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int[][] getChess() {
        return chess;
    }

    public void setChess(int[][] chess) {
        this.chess = chess;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public int getVisitsCount() {
        return visitsCount;
    }

    public void setVisitsCount(int visitsCount) {
        this.visitsCount = visitsCount;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void increaseReward(double reward){
        this.reward += reward;
    }

    public void increaseVisitCount(){
        this.visitsCount += 1;
    }
}
