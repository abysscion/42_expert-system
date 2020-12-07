package AST.NonTerminals;

import AST.ASTNode;
import AST.NonTerminal;

public class And extends NonTerminal {
    public boolean interpret() {
        return left.interpret() && right.interpret();
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.AND, right);
    }
}
