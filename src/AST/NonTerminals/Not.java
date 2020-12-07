package AST.NonTerminals;

import AST.ASTNode;
import AST.NonTerminal;

public class Not extends NonTerminal {
    public void setChild(ASTNode child) {
        setLeft(child);
    }

    public void setRight(ASTNode right) {
        throw new UnsupportedOperationException();
    }

    public boolean interpret() {
        return !left.interpret();
    }

    public String toString() {
        return String.format("%s%s", ASTNode.NOT, left);
    }
}
