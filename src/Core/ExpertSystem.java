package Core;

public class ExpertSystem {
    public static void main(String[] args){
        try {
            if (args.length == 0)
                printUsage();
            else {
                var filePath = args[args.length - 1];
                var engine = new InferenceEngine();
                var globalGraph = new GlobalGraph();

                Parser.parseArguments(args);
                engine.parseFile(filePath);
                globalGraph.buildGraph(engine.getCount(), engine.getRuleTrees(), engine.getAtomicFacts());
                globalGraph.printAnswer(engine.getQueries());

                if (Parser.getIFlag())
                    engine.enterInteractiveMode(globalGraph);
            }
        }
        catch (Exception e) {
            var msg = e.getMessage();
            if (msg != null)
                System.out.println("[Error] " + msg);
            else
                System.out.println("[Error] " + e.toString());
        }
    }

    private static void printUsage() {
        System.out.println("Usage: expert-system [options] <path/to/input_file.txt>");
        System.out.println("options:\n" +
                "\t-i\t - enter interactive mode.\n" +
                "\t-c\t - if you want to make sure that the result matches checklist.\n" +
                "\t-x\t - ignore condition demand in rules (=> or <=> will be not necessary).\n");
    }
}
