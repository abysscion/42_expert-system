package AST;

public abstract class Terminal implements ASTNode {
    protected String name;
    protected boolean value;


    public ASTNode getLeft(ASTNode astNode){
        return null;
    }

    public ASTNode getRight(ASTNode astNode) {
        return null;
    }


    public Terminal(String name) {
        this(name, false);
    }

    public Terminal(String name, boolean value) {
        this.value = value;
        this.name = name;
    }


    public String toString() {
        return String.format("%s", name);
    }
}
