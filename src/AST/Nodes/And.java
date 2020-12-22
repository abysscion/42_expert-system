package AST.Nodes;

import AST.ASTNode;
import AST.Node;

public class And extends Node {
    public And(String value) {
        super(value);
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.AND, right);
    }
}
