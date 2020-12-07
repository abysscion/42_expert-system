package Core;

import java.io.File;
import java.util.regex.Pattern;

public class Parser {
    private boolean IFlag;
    private boolean VFlag;

    public boolean GetIFlag() { return IFlag; }

    public boolean GetVFlag() { return VFlag; }

    public static String getRulePartFromLine(String line) {
        if (line == null)
            throw new NullPointerException();
        if (line.length() == 0)
            return "";
        var tmpLine = line.replaceAll("^[ \t]+|[ \t]+$", "");
        if (tmpLine.charAt(0) == '?' || tmpLine.charAt(0) == '=')
            return "";
        return tmpLine;
    }

    public static boolean isRuleValid(String line) {
        if (line == null)
            throw new NullPointerException();
        if (line.length() == 0)
            return false;
        if (!line.contains("=>"))
            return false;

        var tokens = line.split("\\s+");
        var operatorsCount = 0;
        var operandsCount = 0;
        for (var token : tokens) {
            if (Pattern.matches("^(([+|^])|(=>)|(<=>))$", token)) //skipping ! operator
                operatorsCount++;
            else
                operandsCount++;
        }
        return operatorsCount == operandsCount - 1;
    }

    public static boolean isLineValid(String line) {
        if (line == null)
            throw new NullPointerException();
        if (line.length() == 0)
            return true;

        var matcher = Pattern.compile("[^()!+|^=?<>A-Z\\s ]+").matcher(line);
        if (matcher.find())
            return line.charAt(matcher.start()) == '#';
        return true;
    }

    public void parseArguments(String[] args) throws Exception {
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            var argument = args[i];
            switch (argument) {
                case "-v":
                    if (!VFlag)
                        VFlag = true;
                    else
                        throw new Exception("-v flag is already set!");
                    break;
                case "-i":
                    if (!IFlag)
                        IFlag = true;
                    else
                        throw new Exception("-i flag is already set!");
                    break;
                default:
                    if (i != argsLength - 1)
                        throw new Exception("unknown argument provided or file path is not last argument");

                    var file = new File(argument);
                    if (!file.isFile())
                        throw new Exception("file [" + argument + "] does not exist!");
                    break;
            }
        }
    }
}
