package AST.Terminals;

import AST.ASTNode;
import AST.Terminal;

public class Fact extends Terminal {
    public Fact(String name) {
        super(name);
    }
    public Fact(String name, boolean value) {
        super(name, value);
    }

    public boolean interpret() {
        //TODO: do something with facts?
        String a = null;
        return a.length() == 1;
    }

    @Override
    public ASTNode getLeft() {
        return null;
    }

    @Override
    public ASTNode getRight() {
        return null;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
