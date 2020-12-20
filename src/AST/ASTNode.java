package AST;

public interface ASTNode {
    String NOT = "!";
    String AND = "+";
    String OR = "|";
    String XOR = "^";
    String IMP = "=>";
    String BI = "<=>";

    boolean interpret();
    String getValue();
    ASTNode getLeft();
    ASTNode getRight();
}
