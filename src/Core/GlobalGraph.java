package Core;

import AST.ASTNode;
import AST.ASTNodeFabric;

import javax.swing.tree.TreeNode;
import java.util.*;
import java.util.stream.Stream;

public class GlobalGraph {
    private Map<String, String> answers = new HashMap<>();
    private final String trueFact = "TRUE";
    private final String falseFact = "FALSE";
    private final String indeterminatelyFact = "INDETERMINATELY";

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
        for (String fact : atomicFacts.keySet()) {
            answers.put(fact, formAnswer(globalState, atomicFacts, fact));
        }
        if (Parser.getCFlag()) {
            Set<String> left = new HashSet<>();
            Set<String> right = new HashSet<>();
            for (ASTNode tree : ruleTrees) {
                if (tree.getValue().equals("=>")) {
                    left.addAll(getAllAtomicFactsFromTree(tree.getLeft()));
                    right.addAll(getAllAtomicFactsFromTree(tree.getRight()));
                }
            }
            left.removeAll(right);
            for (String f : left) {
                if (answers.get(f).equals(indeterminatelyFact)) {
                    ASTNode notNode = ASTNodeFabric.CreateNode("!");
                    notNode.setLeft(ASTNodeFabric.CreateNode(f));
                    globalAnd(globalState, execute(notNode, atomicFacts));
                }
            }

            if (!checkContradiction(globalState)) {
                System.out.println("\"The logical system is contradictory, every possible fact follows from it!\\n\" +\n" +
                        "                            \"But it became contradictory after following illogical directions of the subject!\"");
                System.exit(0);
            }
            for (String fact : atomicFacts.keySet()) {
                String temp = formAnswer(globalState, atomicFacts, fact);
                answers.put(fact, temp.equals(indeterminatelyFact) ? falseFact : temp);
            }
        }
    }

    public void printAnswer(HashSet<String> queries) {
        for (String query : queries){
            if (answers.containsKey(query)){
                System.out.println(query + " = " + answers.get(query));
            }
            else
                System.out.println(query + " = FALSE BY DEFAULT");
        }
//        for (String query : queries) {
//            printAnswerForExactFact(query);
//        }
    }

//    public void printAnswerForExactFact(String factName) {
//        if (answers.containsKey(factName)) {
//            var answer = answers.get(factName).equals(trueFact);
//            System.out.printf("%s = %s%s\033[0m\n", factName, answer ? "\033[0;32m" : "\033[0;31m", answer);
//        } else
//            System.out.printf("%s = \033[0;31mFALSE BY DEFAULT\033[0m\n", factName);
//    }

    public boolean checkContradiction(ArrayList<Boolean> globalState) {
        return globalState.contains(false);
    }

    String formAnswer(ArrayList<Boolean> globalState, Map<String, ArrayList<Boolean>> atomicFacts, String fact) {//globalState==assumptions
        boolean factIsFollow = doesItFollow(globalState, atomicFacts.get(fact));
        boolean negationIsFollow = doesItFollow(globalState, BooleanFunction.lnot(atomicFacts.get(fact), null));//lnot atomicFacts.get(fact)
        if (factIsFollow)
            return trueFact;
        else if (negationIsFollow)
            return falseFact;
        else
            return indeterminatelyFact;
    }

    public Set<String> getAllAtomicFactsFromTree(ASTNode tree) {
        Set<String> factsFromTree = new HashSet<>();

        if (tree.getLeft() != null) {
            factsFromTree.addAll(getAllAtomicFactsFromTree(tree.getLeft()));
        }
        char fact = tree.getValue().toCharArray()[0];
        if (fact >= 'A' && fact <= 'Z') {
            factsFromTree.add(tree.getValue());
        }
        if (tree.getRight() != null) {
            factsFromTree.addAll(getAllAtomicFactsFromTree(tree.getRight()));
        }
        return factsFromTree;
    }

    boolean doesItFollow(ArrayList<Boolean>assumption, ArrayList<Boolean>consequence){ //Check return assumption & consequence == consequence
        for (int i = 0; i < assumption.size(); i++) {
            if ((assumption.get(i) & consequence.get(i)) != consequence.get(i))
                return false;
        }
        return true;
    }
}
