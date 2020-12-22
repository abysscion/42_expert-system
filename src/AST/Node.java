package AST;

public abstract class Node implements ASTNode {
    protected ASTNode left, right;
    protected String value;

    public Node(String value) {
        this.value = value;
    }

    public void setLeft(ASTNode left) {
        this.left = left;
    }

    public void setRight(ASTNode right) {
        this.right = right;
    }

    @Override
    public String getValue() {
        return value;
    }
    @Override
    public ASTNode getLeft(){
        return left;
    }
    @Override
    public ASTNode getRight() {
        return right;
    }
}
