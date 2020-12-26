package Core;

import java.time.format.DateTimeFormatter;

public class Utilities {
    private Utilities() {
    }

    public static <T extends Throwable> void HandleException(T ex) throws T {
        if (Parser.getDFlag()) {
            System.out.printf("[%s] [Error] [%s]\n", java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), ex.getMessage());
        } else
            throw ex;
    }
}
