package AST.NonTerminals;

import AST.ASTNode;
import AST.Node;

public class Or extends Node {
    public Or(String value) {
        super(value);
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.OR, right);
    }
}
