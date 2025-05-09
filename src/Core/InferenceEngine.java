package Core;

import AST.*;
import AST.Nodes.Not;
import AST.Nodes.Fact;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class InferenceEngine {
    private final HashMap<String, Fact> knownFacts;
    private final HashMap<String, ArrayList<Boolean>> atomicFacts;
    private final ArrayList<Fact> atoms;
    private final ArrayList<ASTNode> ruleTrees;
    private final HashSet<String> queries;
    private int count;

    public InferenceEngine()
    {
        atomicFacts = new HashMap<>();
        ruleTrees = new ArrayList<>();
        queries = new HashSet<>();
        knownFacts = new HashMap<>();
        atoms = new ArrayList<>();
    }

    public void enterInteractiveMode(GlobalGraph graph) throws IOException {
        var reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.println("\n*INTERACTIVE MODE*\n");
            System.out.println("Rules:");
            for (int i = 0, ruleTreesSize = ruleTrees.size(); i < ruleTreesSize; i++)
                System.out.printf("\t%d) %s\n", i + 1, ruleTrees.get(i));
            System.out.print("\nEnter query: ");
            var input = reader.readLine();
            if (input.equals("exit") || input.equals("quit"))
                break;
            input = input.replaceAll("\\s+", "").toUpperCase();
            if (!input.matches("[A-Z]+"))
                System.out.println("Wrong query format! Example: ABC");
            else {
                for (char fact : input.toCharArray())
                    graph.printAnswerForExactFact("" + fact);
            }
        }
    }

    public void parseFile(String fileName) throws Exception {
        try {
            if (!new File(fileName).exists())
                Utilities.HandleException(new Exception("file [" + fileName + "] not found!"));
            var reader = new BufferedReader(new FileReader(fileName));
            var line = "";

            while ((line = reader.readLine()) != null) {
                if (!Parser.isLineValid(line))
                    Utilities.HandleException(new Exception("invalid line: \"" + line + "\""));
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
                    if (Parser.isRuleValid(line, Parser.getXFlag())) {
                        ASTNode temp = buildTreeFromRule(line);
                        ruleTrees.add(temp);
                    }
                }
            }
            atoms.addAll(knownFacts.values());

            //some shit happened here but no one actually cares
            for (var atom : atoms) {
                if (Parser.getDFlag())
                    System.out.print(atom.toString() + ": ");
                for (var bit : getAtomicDigitAsBooleans(atom)) {
                    //if (Parser.getDFlag())
                        //System.out.print(bit ? '1' : '0');
                }
                //if (Parser.getDFlag())
                    //System.out.println();
            }
        }
        catch (IOException e) {
            Utilities.HandleException(new Exception("unable to read file: " + fileName));
        }
    }

    private void parseQueries(String line) {
        if (line == null)
            Utilities.HandleException(new NullPointerException());
        if (line.length() > 1 && line.startsWith("?")) {
            line = line.replaceAll("\\s+", "").substring(1);
            if (!line.matches("[A-Z]+"))
                return;
            var arr = line.toCharArray();
            for (var i = 0; i < line.length(); i++)
                queries.add(arr[i] + "");
        }
    }

    private void parseKnownFacts(String line) throws Exception {
        if (line == null)
            Utilities.HandleException(new NullPointerException());
        if (line.length() > 1 && line.startsWith("=")) {
            line = line.replaceAll("\\s+", "").substring(1);
            if (!line.matches("[A-Z]+"))
                return;
            char[] arr = line.toCharArray();
            for (var i = 0; i < line.length(); i++) {
                ruleTrees.add(buildTreeFromRule(String.valueOf(arr[i])));
                var name = arr[i] + "";
                knownFacts.put(name, new Fact(name));
            }
            if (knownFacts.size() >= 20)
                Utilities.HandleException(new Exception("too many facts! 20+ facts could cause HUGE problems.\nIf you are brave enough use -d flag to ignore it :)"));
        }
    }

    private ArrayList<Boolean> getAtomicDigitAsBooleans(Fact atomicFact) throws Exception {
        if (atoms.size() == 0)
            Utilities.HandleException(new Exception("tried to get atomic digit from empty atoms list"));

        var N = atoms.size();
        var place = atoms.indexOf(atomicFact);
        var zeroAndOneCount = (int)Math.pow(2, N - place - 1);
        var seriesRepeating = (int)Math.pow(2, place);

        ArrayList<Boolean> result = new ArrayList<>(Collections.nCopies(seriesRepeating * zeroAndOneCount * 2, false));

        for (var i = 0; i < seriesRepeating; i++) {
            for (var j = 0; j < zeroAndOneCount; j++)
                result.set(j + (zeroAndOneCount * i * 2), true);
        }
        count = result.size();
        atomicFacts.put(atomicFact.toString(), result);
        return result;
    }

    private ASTNode buildTreeFromRule(String rule) {
        var pat = Pattern.compile("(\\(\\S)|(\\S\\))|(!\\S)");
        var matcher = pat.matcher(rule);
        var deq = new LinkedList<String>();

        while (matcher.find()) {
            rule = new StringBuilder(rule).insert(matcher.start() + 1, ' ').toString();
            matcher = pat.matcher(rule);
        }
        Collections.addAll(deq, rule.split("\\s+"));

        for (var fact:deq) {
            if (fact.matches("^[A-Z]$") && !knownFacts.containsKey(fact)) {
                knownFacts.put(fact, new Fact(fact));
            }
        }
        var rpnTokensList = InfixToPostfixParser.shuntingYard(deq);
        var stack = new Stack<ASTNode>();
        for (var token : rpnTokensList) {
            if (ASTParser.isOperator(token)) {
                ASTNode node = ASTNodeFabric.CreateNode(token);

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
            else {

                stack.push(ASTNodeFabric.CreateNode(token));
            }
        }

        return stack.pop();
    }

    public HashMap<String, ArrayList<Boolean>> getAtomicFacts() {
        return atomicFacts;
    }

    public ArrayList<ASTNode> getRuleTrees() {
        return ruleTrees;
    }

    public HashSet<String> getQueries() {
        return queries;
    }

    public int getCount() {
        return count;
    }
}
