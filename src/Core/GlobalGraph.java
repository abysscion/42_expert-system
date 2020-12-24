package Core;

import AST.ASTNode;
import AST.ASTNodeFabric;

import javax.swing.tree.TreeNode;
import java.util.*;
import java.util.stream.Stream;

public class GlobalGraph {
    boolean[] execute(ASTNode tree, Map<String, boolean[]> atomicFacts){
        if (tree == null || tree.getValue() == null || tree.getValue().equals("")){
            return null;
        }
        else if (atomicFacts.containsKey(tree.getValue()))
            return atomicFacts.get(tree.getValue());

        return perform(tree.getValue(), tree.getLeft(), tree.getRight(),
                atomicFacts);
    }

    boolean[] perform(String op, ASTNode left, ASTNode right, Map<String, boolean[]> atomicFacts){
        BooleanFunctionFabric fabric = new BooleanFunctionFabric();
        return fabric.selectOperation(op, execute(left,atomicFacts), execute(right, atomicFacts));
    }

    void buildGraph(int count, ArrayList<ASTNode> ruleTrees, Map<String, boolean[]> atomicFacts){
        boolean[] globalState = new boolean[count];
        for (ASTNode rule : ruleTrees){
            globalAnd(globalState, execute(rule, atomicFacts));
        }
        System.out.println(Arrays.toString(globalState));
    }

    public void globalAnd(boolean[] globalState, boolean[] execute){
        System.out.println(Arrays.toString(globalState));
        for (int i = 0; i < globalState.length; i++){
            globalState[i] = globalState[i] | execute[i];
        }
    }
}
