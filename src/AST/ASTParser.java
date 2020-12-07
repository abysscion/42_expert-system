package AST;

public abstract class ASTParser {
    private ASTParser() {}

    public static boolean isOperator(String token) {
        switch (token) {
            case ASTNode.NOT:
            case ASTNode.AND:
            case ASTNode.OR:
            case ASTNode.XOR:
            case ASTNode.IMP:
            case ASTNode.BI:
                return true;

            default:
                return false;
        }
    }
}
