package Core;

import AST.*;
import AST.NonTerminals.Not;
import AST.Terminals.Fact;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;

public class InferenceEngine {
    private final HashMap<String, Fact> knownFacts;
    private final HashMap<String, boolean[]> atomicFacts;
    private final ArrayList<Fact> atoms;
    private final ArrayList<ASTNode> ruleTrees;
    private final HashSet<String> queries;
    private final boolean verboseFlag;

    public InferenceEngine(boolean verboseFlag)
    {
        atomicFacts = new HashMap<>();
        this.verboseFlag = verboseFlag;
        ruleTrees = new ArrayList<>();
        queries = new HashSet<>();
        knownFacts = new HashMap<>();
        atoms = new ArrayList<>();
    }

    public void enterInteractiveMode() {

    }

    public void evaluateFile(String filePath) throws Exception {
        GlobalGraph globalGraph = new GlobalGraph();//temp
        parseFile(filePath);
        for (var atom:atoms) {
            System.out.print(atom.toString() + ": ");
            for (var bit:getAtomicDigitAsBooleans(atom))
                System.out.print(bit ? '1' : '0');
            System.out.println();
        }
        int i = 0;
        for (Map.Entry<String, boolean[]> f : atomicFacts.entrySet()){
            globalGraph.execute(ruleTrees.get(i), f.getKey(), f.getValue()); //send to recursion
            i++;
        }
    }

    private void evaluateLine() {

    }

    private void parseFile(String fileName) throws Exception {
        try {
            if (!new File(fileName).exists())
                throw new Exception("file [" + fileName + "] not found!");
            var reader = new BufferedReader(new FileReader(fileName));
            var line = "";

            while ((line = reader.readLine()) != null) {
                if (!Parser.isLineValid(line))
                    throw new Exception("invalid line: \"" + line + "\"");
                line = line.substring(0, line.contains("#") ? line.indexOf('#') : line.length());
                line = line.strip();
                if (line.startsWith("="))
                    parseKnownFacts(line);
                else if (line.startsWith("?"))
                    parseQueries(line);
                else {
                    line = Parser.getRulePartFromLine(line);
                    if (line.length() == 0)
                        continue;
                    if (Parser.isRuleValid(line)) {
                        ASTNode temp = buildTreeFromRule(line);
                        ruleTrees.add(temp);
                    }
                }
            }
            atoms.addAll(knownFacts.values());
        }
        catch (IOException e) {
            throw new Exception("unable to read file: " + fileName);
        }
    }

    private void parseQueries(String line) {
        if (line == null)
            throw new NullPointerException();
        if (line.length() > 1 && line.startsWith("?")) {
            if (Pattern.matches("[^A-Za-z]", line))
                return;
            var arr = line.toCharArray();
            for (var i = 1; i < line.length(); i++)
                queries.add(arr[i] + "");
        }
    }

    private void parseKnownFacts(String line) {
        if (line == null)
            throw new NullPointerException();
        if (line.length() > 1 && line.startsWith("=")) {
            if (!Pattern.matches("^[A-Z]$", line)) //TODO: throw exception for invalid known fact?
                return;
            var arr = line.toCharArray();
            for (var i = 1; i < line.length(); i++) {
                var name = arr[i] + "";
                knownFacts.put(name, new Fact(name, name));
            }
        }
    }

    private boolean[] getAtomicDigitAsBooleans(Fact atomicFact) throws Exception {
        if (atoms.size() == 0)
            throw new Exception("tried to get atomic digit from empty atoms list");

        var N = atoms.size();
        var place = atoms.indexOf(atomicFact);
        var zeroAndOneCount = (int)Math.pow(2, N - place - 1);
        var seriesRepeating = (int)Math.pow(2, place);

        boolean[] result = new boolean[seriesRepeating * zeroAndOneCount * 2];

        for (var i = 0; i < seriesRepeating; i++) {
            for (var j = 0; j < zeroAndOneCount; j++)
                result[j + (zeroAndOneCount * i * 2)] = true;
        }
        atomicFacts.put(atomicFact.toString(), result);
        return result;
    }

    //TODO: idk how to make it work
//    private BigDecimal getAtomicDigitAsBigInt(Fact atomicFact) throws Exception {
//        if (atoms.size() == 0)
//            throw new Exception("tried to get atomic digit from empty atoms list");
//
//        var N = atoms.size();
//        var place = atoms.indexOf(atomicFact);
//        var zeroAndOneCount = (int)Math.pow(2, N - place - 1);
//        var seriesRepeating = (int)Math.pow(2, place);
//
//        boolean[] tmp = new boolean[seriesRepeating * zeroAndOneCount * 2];
//
//        for (var i = 0; i < seriesRepeating; i++) {
//            for (var j = 0; j < zeroAndOneCount; j++)
//                tmp[j + zeroAndOneCount * ((2 * i) + 1)] = true;
//        }
//
//        long val = 0;
//        for (var bit: tmp) {
//            val <<= 1;
//            if (bit)
//                val++;
//        }
//
//        return BigDecimal.valueOf(val);
//    }

    private ASTNode buildTreeFromRule(String rule) {
        var pat = Pattern.compile("(\\(\\S)|(\\S\\))|(!\\S)");
        var matcher = pat.matcher(rule);
        var deq = new LinkedList<String>();

        while (matcher.find()) {
            rule = new StringBuilder(rule).insert(matcher.start() + 1, ' ').toString();
            matcher = pat.matcher(rule);
        }
        Collections.addAll(deq, rule.split("\\s+"));

        for (var fact:deq) { //TODO: throw exception for invalid known fact?
            if (fact.matches("^[A-Z]$") && !knownFacts.containsKey(fact)) {
                knownFacts.put(fact, new Fact(fact, fact));
            }
        }

        var rpnTokensList = InfixToPostfixParser.shuntingYard(deq);
        var stack = new Stack<ASTNode>();

        for (var token : rpnTokensList) {
            if (ASTParser.isOperator(token)) {
                var node = (NonTerminal) ASTNodeFabric.CreateNode(token);

                if (token.equals(ASTNode.NOT))
                    ((Not)node).setChild(stack.pop());
                else {
                    var rOperand = stack.pop();
                    var lOperand = stack.pop();

                    node.setLeft(lOperand);
                    node.setRight(rOperand);
                }
                stack.push(node);
            }
            else
                stack.push(ASTNodeFabric.CreateNode(token));
        }

        return stack.pop();
    }
}
