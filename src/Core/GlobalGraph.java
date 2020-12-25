package Core;

import AST.ASTNode;
import AST.ASTNodeFabric;

import javax.swing.tree.TreeNode;
import java.util.*;
import java.util.stream.Stream;

public class GlobalGraph {
    ArrayList<Boolean> execute(ASTNode tree, Map<String, ArrayList<Boolean>> atomicFacts) {
        if (tree == null || tree.getValue() == null || tree.getValue().equals("")) {
            return null;
        } else if (atomicFacts.containsKey(tree.getValue()))
            return atomicFacts.get(tree.getValue());

        return perform(tree.getValue(), tree.getLeft(), tree.getRight(),
                atomicFacts);
    }

    ArrayList<Boolean> perform(String op, ASTNode left, ASTNode right, Map<String, ArrayList<Boolean>> atomicFacts) {
        BooleanFunctionFabric fabric = new BooleanFunctionFabric();
        return fabric.selectOperation(op, execute(left, atomicFacts), execute(right, atomicFacts));
    }

    public void globalAnd(ArrayList<Boolean> globalState, ArrayList<Boolean> execute) {
        for (int i = 0; i < globalState.size(); i++) {
            globalState.set(i, globalState.get(i) | execute.get(i));
        }
    }

    void buildGraph(int count, ArrayList<ASTNode> ruleTrees, Map<String, ArrayList<Boolean>> atomicFacts) {
        ArrayList<Boolean> globalState = new ArrayList<>(Collections.nCopies(count, false));
        for (ASTNode rule : ruleTrees) {
            globalAnd(globalState, execute(rule, atomicFacts));
        }
        if (!checkContradiction(globalState)) {
            System.out.println("System has a contradiction!!!!!!!!!");
            System.exit(0);
        }
        for (String fact : atomicFacts.keySet()){
            System.out.println(fact + " == " + formAnswer(globalState, atomicFacts, fact));
        }
    }

    public boolean checkContradiction(ArrayList<Boolean> globalState) {
        return globalState.contains(false);
    }

    String formAnswer(ArrayList<Boolean> globalState, Map<String, ArrayList<Boolean>> atomicFacts, String fact){//globalState==assumptions
        boolean factIsFollow = doesItFollow(globalState, atomicFacts.get(fact));
        boolean negationIsFollow = doesItFollow(globalState, BooleanFunction.lnot(atomicFacts.get(fact), null));//lnot atomicFacts.get(fact)
        if (factIsFollow)
            return "TRUE";
        else if (negationIsFollow)
            return "FALSE";
        else
            return "INDETERMINATELY";
    }
    boolean doesItFollow(ArrayList<Boolean>assumption, ArrayList<Boolean>consequence){ //Check return assumption & consequence == consequence
        for (int i = 0; i < assumption.size(); i++) {
           if (assumption.get(i) & consequence.get(i) != consequence.get(i))
               return false;
        }
        return true;
    }
}
