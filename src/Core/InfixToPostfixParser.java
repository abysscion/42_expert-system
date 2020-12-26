package Core;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static AST.ASTNode.*;

public class InfixToPostfixParser {
    private static final Map<String, Integer> defaultPrecedenceMap;

    static {
        defaultPrecedenceMap = new HashMap<>();
        defaultPrecedenceMap.put(NOT, 1);
        defaultPrecedenceMap.put(AND, 2);
        defaultPrecedenceMap.put(OR, 3);
        defaultPrecedenceMap.put(XOR, 4);
        defaultPrecedenceMap.put(IMP, 5);
        defaultPrecedenceMap.put(BI, 6);
    }

    public static List<String> shuntingYard(final Deque<String> tokens) {
        return shuntingYard(tokens, defaultPrecedenceMap);
    }

    public static List<String> shuntingYard(final Deque<String> tokens, final Map<String, Integer> precedenceMap) {
        final Deque<String> operatorStack = new LinkedList<>();
        final Deque<String> outputQueue = new LinkedList<>();
        String previousToken = "";

        while (!tokens.isEmpty()) {
            final String currentToken = tokens.removeFirst();

            if (isVariableOrConstantName(currentToken))
                outputQueue.add(currentToken);
            else if (isOperator(currentToken)) {
                while (!operatorStack.isEmpty()) {
                    if (isOperator(operatorStack.getLast()) &&
                            precedenceCompare(operatorStack.getLast(), currentToken, precedenceMap)) {
                        outputQueue.addLast(operatorStack.removeLast());
                    }
                    else
                        break;
                }
                operatorStack.addLast(currentToken);
            }
            else if (currentToken.equals("(")) {
                if (isVariableOrConstantName(previousToken))
                    return null;
                operatorStack.addLast("(");
            }
            else if (currentToken.equals(")")) {
                if (!isVariableOrConstantName(previousToken))
                    return null;
                while (!operatorStack.isEmpty() && !operatorStack.getLast().equals("("))
                    outputQueue.addLast(operatorStack.removeLast());
                if (operatorStack.isEmpty())
                    return null;
                else
                    operatorStack.removeLast();
            }
            else {
                Utilities.HandleException(new IllegalStateException("Could not recognize a token: " + currentToken));
            }

            previousToken = currentToken;
        }

        while (!operatorStack.isEmpty()) {
            final String operator = operatorStack.removeLast();
            if (operator.equals("(") || operator.equals(")"))
                return null;
            outputQueue.addLast(operator);
        }

        return new ArrayList<>(outputQueue);
    }

    private static boolean precedenceCompare(final String stackTopToken, final String token,
                                             final Map<String, Integer> precedenceMap) {
        return precedenceMap.get(stackTopToken) < precedenceMap.get(token);
    }

    private static boolean isVariableOrConstantName(final String token) {
        if (isOperator(token))
            return false;
        if (token.equals("("))
            return false;
        if (token.equals(")"))
            return false;
        return !token.isEmpty();
    }

    private static boolean isOperator(final String token) {
        switch (token) {
            case "!":
            case "+":
            case "|":
            case "^":
            case "=>":
            case "<=>":
                return true;

            default:
                return false;
        }
    }
}
