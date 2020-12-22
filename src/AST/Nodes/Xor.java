package AST.Nodes;

import AST.ASTNode;
import AST.Node;

public class Xor extends Node {
    public Xor(String value) {
        super(value);
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.XOR, right);
    }
}
