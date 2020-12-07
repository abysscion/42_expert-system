package AST;

import AST.NonTerminals.*;
import AST.Terminals.Fact;

public abstract class ASTNodeFabric {
    private ASTNodeFabric(){}

    public static ASTNode CreateNode(String token) {
        switch (token) {
            case ASTNode.NOT:
                return new Not();
            case ASTNode.AND:
                return new And();
            case ASTNode.OR:
                return new Or();
            case ASTNode.XOR:
                return new Xor();
            case ASTNode.IMP:
                return new Implication();
            case ASTNode.BI:
                return new Biconditional();

            default:
                return new Fact(token);
        }
    }
}
