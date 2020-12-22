package AST;

import AST.Nodes.*;

public abstract class ASTNodeFabric {
    private ASTNodeFabric(){}

    public static ASTNode CreateNode(String token) {
        switch (token) {
            case ASTNode.NOT:
                return new Not(token);
            case ASTNode.AND:
                return new And(token);
            case ASTNode.OR:
                return new Or(token);
            case ASTNode.XOR:
                return new Xor(token);
            case ASTNode.IMP:
                return new Implication(token);
            case ASTNode.BI:
                return new Biconditional(token);

            default:
                return new Fact(token);
        }
    }
}
