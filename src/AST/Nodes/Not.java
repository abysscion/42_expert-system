package AST.Nodes;

import AST.ASTNode;
import AST.Node;

public class Not extends Node {
    public Not(String value) {
        super(value);
    }

    public void setChild(ASTNode child) {
        setLeft(child);
    }

    public void setRight(ASTNode right) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return String.format("%s%s", ASTNode.NOT, left);
    }
}
