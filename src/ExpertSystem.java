public class ExpertSystem {

    public static void main(String[] args) {
        try {
            if (args.length == 0)
                printUsage();
            else {
                var filePath = args[args.length - 1];
                var engine = new InferenceEngine();

                Parser.parseArguments(args);
                Parser.parseFile(filePath);
                if (Parser.iFlag)
                    engine.enterInteractiveMode();
                else
                    engine.evaluateFile(filePath);
            }
        }
        catch (Exception e) {
            var msg = e.getMessage();
            if (msg != null)
                System.out.println("[Error] " + msg);
            else
                System.out.println("[Error] " + e.toString());
            e.printStackTrace(); //TODO: remove
        }
    }

    private static void printUsage() {
        System.out.println("Usage: expert-system [options] <path/to/input_file.txt>");
        System.out.println("options:\n" +
                "\t-i\t - enter interactive mode.\n" +
                "\t-e\t - print some explanations as solving is going on.");
    }
}
