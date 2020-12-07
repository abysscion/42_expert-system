package AST.NonTerminals;

import AST.ASTNode;
import AST.NonTerminal;

public class Biconditional extends NonTerminal {
    public boolean interpret() {
        var l = left.interpret();
        var r = right.interpret();

        return ((l && r) || (!l && !r));
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.BI, right);
    }
}
