package Core;

public class ExpertSystem {
    public static void main(String[] args) throws Exception {
        var filePath = "C:\\Users\\Mi\\Desktop\\42_expert-system\\example_input.txt";
        var parser = new Parser();
        var engine = new InferenceEngine(parser.GetVFlag());

      //  parser.parseArguments(args);
        if (parser.GetIFlag())
            engine.enterInteractiveMode();
        else
            engine.evaluateFile(filePath);
        /*try {
            if (args.length == 0)
                printUsage();
            else {
                var filePath = args[args.length - 1];
                var parser = new Parser();
                var engine = new InferenceEngine(parser.GetVFlag());

                parser.parseArguments(args);
                if (parser.GetIFlag())
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
        }*/
    }

    private static void printUsage() {
        System.out.println("Usage: expert-system [options] <path/to/input_file.txt>");
        System.out.println("options:\n" +
                "\t-i\t - enter interactive mode.\n" +
                "\t-v\t - print some explanations as solving is going on.");
    }
}
