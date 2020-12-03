public class ExpertSystem {

    public static void main(String[] args) {
        try {
            if (args.length == 0)
                enterInteractiveMode();
            else {
                Parser.ParseArguments(args);
                Parser.ParseFile(args[args.length - 1]);
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

    private static void enterInteractiveMode() {

    }

    private static void printUsage() {
        System.out.println("Usage: expert-system [options] <path/to/input_file.txt>");
        System.out.println("options:\n" +
                "\t-i\t - enter interactive mode.\n" +
                "\t-e\t - print some explanations as solving is going on.");
    }
}
