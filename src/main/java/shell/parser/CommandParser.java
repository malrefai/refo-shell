package shell.parser;

import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private CommandParser() {
        /* This utility class should not be instantiated */
    }

    /*
     Splits a raw input string into a list of parts.
     Example: "echo   hello world" -> ["echo", "hello", "world"]
    */
    public static List<String> parse(String input) {
        if (input == null || input.isBlank()) return List.of();

        return Arrays.asList(input.strip().split("\\s+"));
    }
}
