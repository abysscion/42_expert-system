package AST.Terminals;

import AST.ASTNode;
import AST.Terminal;

public class Fact extends Terminal {
    public Fact(String name, String value) {
        super(name, value);
    }

    @Override
    public ASTNode getLeft() {
        return null;
    }

    @Override
    public ASTNode getRight() {
        return null;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
