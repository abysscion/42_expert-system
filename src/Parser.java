import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Parser {
    public static boolean eFlag;
    public static boolean iFlag;
    public static String allowedSymbolsReg = "[^()!+|^=?<>A-Z\\s ]+";

    public static void parseArguments(String[] args) throws Exception {
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            var argument = args[i];
            switch (argument) {
                case "-i":
                    if (!iFlag)
                        iFlag = true;
                    else
                        throw new Exception("-i flag is already set!");
                    break;
                case "-e":
                    if (!eFlag)
                        eFlag = true;
                    else
                        throw new Exception("-e flag is already set!");
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

    public static void parseFile(String fileName) throws Exception {
        try {
            if (!new File(fileName).exists())
                throw new Exception("file [" + fileName + "] not found!");
            var reader = new BufferedReader(new FileReader(fileName));
            var line = "";

            while ((line = reader.readLine()) != null) {
                if (!isLineValid(line))
                    throw new Exception("invalid line: \"" + line + "\"");
            }
        }
        catch (IOException e) {
            throw new Exception("unable to read file: " + fileName);
        }
    }

    private static boolean isLineValid(String line) {
        if (line == null)
            throw new NullPointerException();
        if (line.length() < 1)
            return true;

        var matcher = Pattern.compile(allowedSymbolsReg).matcher(line);
        if (matcher.find())
            return line.substring(matcher.start()).charAt(0) == '#';
        return true;
    }
}
