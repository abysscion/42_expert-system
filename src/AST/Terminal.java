package AST;

public abstract class Terminal implements ASTNode {
    protected String name;
    protected String value;


    public ASTNode getLeft(ASTNode astNode){
        return null;
    }

    public ASTNode getRight(ASTNode astNode) {
        return null;
    }


    public Terminal(String name, String value) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public String toString() {
        return String.format("%s", name);
    }
}
