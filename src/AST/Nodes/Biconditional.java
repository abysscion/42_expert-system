package AST.NonTerminals;

import AST.ASTNode;
import AST.Node;

public class Biconditional extends Node {
    public Biconditional(String value) {
        super(value);
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.BI, right);
    }
}
