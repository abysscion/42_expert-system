package AST;

public interface ASTNode {
    String NOT = "!";
    String AND = "+";
    String OR = "|";
    String XOR = "^";
    String IMP = "=>";
    String BI = "<=>";

    String getValue();
    ASTNode getLeft();
    ASTNode getRight();
    void setLeft(ASTNode leftNode);
    void setRight(ASTNode rightNode);
}
