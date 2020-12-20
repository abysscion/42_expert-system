package AST;

public interface ASTNode {
    String NOT = "!";
    String AND = "+";
    String OR = "|";
    String XOR = "^";
    String IMP = "=>";
    String BI = "<=>";

    boolean interpret();
    ASTNode getLeft();
    ASTNode getRight();
}
