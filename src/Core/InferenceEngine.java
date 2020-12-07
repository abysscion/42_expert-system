import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class InferenceEngine {
    private final boolean verboseFlag;
    private final HashSet<FactConnectorNode> knownConnectors;
    private final HashSet<FactNode> knownFacts;

    public InferenceEngine(boolean verboseFlag)
    {
        this.verboseFlag = verboseFlag;
        knownFacts = new HashSet<>();
        knownConnectors = new HashSet<>();
    }

    public void enterInteractiveMode() {

    }

    public void evaluateFile(String filePath) throws Exception {
        parseFile(filePath);
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
                line = Parser.getRulePartFromLine(line);
                if (line.length() == 0)
                    continue;
                if (Parser.isRuleValid(line))
                    parseTokensFromRule(line);
            }
        }
        catch (IOException e) {
            throw new Exception("unable to read file: " + fileName);
        }
    }

    private void parseTokensFromRule(String rule) {
        var tokens = rule.split("\\s+");
        for (var token:tokens) {

        }
    }
}
