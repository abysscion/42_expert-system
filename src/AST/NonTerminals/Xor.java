package AST.NonTerminals;

import AST.ASTNode;
import AST.NonTerminal;

public class Xor extends NonTerminal {
    public Xor(String value) {
        super(value);
    }

    public boolean interpret() {
        return left.interpret() != right.interpret();
    }

    public String toString() {
        return String.format("(%s %s %s)", left, ASTNode.XOR, right);
    }
}
