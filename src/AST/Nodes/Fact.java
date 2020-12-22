package AST.Nodes;

import AST.ASTNode;
import AST.Node;

public class Fact extends Node {
    public Fact(String value) {
        super(value);
    }

    @Override
    public ASTNode getLeft() {
        return null;
    }

    @Override
    public ASTNode getRight() {
        return null;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return String.format("%s", value);
    }
}
