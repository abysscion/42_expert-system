package AST.NonTerminals;

import AST.ASTNode;
import AST.Node;

public class Implication extends Node  {
    public Implication(String value) {
        super(value);
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.IMP, right);
    }
}
