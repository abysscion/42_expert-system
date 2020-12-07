package AST.NonTerminals;

import AST.ASTNode;
import AST.NonTerminal;

public class Implication extends NonTerminal {
    public boolean interpret() {
        return !left.interpret() || right.interpret();
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.IMP, right);
    }
}
