package AST.NonTerminals;

import AST.ASTNode;
import AST.NonTerminal;

public class And extends NonTerminal {
    public And(String value) {
        super(value);
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.AND, right);
    }
}
