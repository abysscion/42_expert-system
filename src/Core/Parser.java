package Core;

import java.io.File;
import java.util.regex.Pattern;

public class Parser {
    private static boolean IFlag; //interactive flag
    private static boolean VFlag; //verbose(explanation printing) flag
    private static boolean XFlag; //ignoring condition (=> or <=>) demand flag
    private static boolean CFlag = true; //if someone wants to make sure that the result matches checklist
    private static boolean DFlag; //debug purposes flag

    public static boolean getDFlag() { return DFlag; }
    public static boolean getIFlag() { return IFlag; }
    public static boolean getVFlag() { return VFlag; }
    public static boolean getCFlag() { return CFlag; }
    public static boolean getXFlag() { return XFlag; }

    public static String getRulePartFromLine(String line) {
        if (line == null)
            Utilities.HandleException(new NullPointerException()); //TODO: RELEASE: replace for exception
        if (line.length() == 0)
            return "";
        var tmpLine = line.replaceAll("^[ \t]+|[ \t]+$", "");
        if (tmpLine.charAt(0) == '?' || tmpLine.charAt(0) == '=')
            return "";
        return tmpLine;
    }

    public static boolean isRuleValid(String line, boolean ignoreConditionDemand) {
        if (line == null)
            Utilities.HandleException(new NullPointerException());
        if (line.length() == 0)
            return false;
        if (!(line.contains("=>") || line.contains("<=>")) && !ignoreConditionDemand)
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
            Utilities.HandleException(new NullPointerException());
        if (line.length() == 0)
            return true;

        var matcher = Pattern.compile("[^()!+|^=?<>A-Z\\s ]+").matcher(line);
        if (matcher.find())
            return line.charAt(matcher.start()) == '#';
        return true;
    }

    public static void parseArguments(String[] args) throws Exception {
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            var argument = args[i];
            switch (argument) {
                case "-D": case "-d":
                    DFlag = true;
                    System.out.println("*DEBUG MODE IS ACTIVE*");
                    break;
                case "-x":
                    if (!XFlag)
                        XFlag = true;
                    else
                        Utilities.HandleException(new Exception("-x flag is already set!"));
                    break;
                case "-v":
                    if (!VFlag)
                        VFlag = true;
                    else
                        Utilities.HandleException(new Exception("-v flag is already set!"));
                    break;
                case "-i":
                    if (!IFlag)
                        IFlag = true;
                    else
                        Utilities.HandleException(new Exception("-i flag is already set!"));
                    break;
                case "-c":
                    if (!CFlag)
                        CFlag = true;
                    else
                        Utilities.HandleException(new Exception("-c flag is already set!"));
                    break;
                default:
                    if (i != argsLength - 1)
                        Utilities.HandleException(new Exception("unknown argument provided or file path is not last argument"));

                    var file = new File(argument);
                    if (!file.isFile())
                        Utilities.HandleException(new Exception("file [" + argument + "] does not exist!"));
                    break;
            }
        }
    }
}
