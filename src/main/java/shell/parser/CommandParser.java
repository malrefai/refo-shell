package shell.parser;

import java.util.List;

public class CommandParser {
    private CommandParser() {
        /* This utility class should not be instantiated */
    }

    public static List<String> parse(String input) {
        // Slice into tokens managing quotes and escape characters
        return Tokenizer.tokenize(input);
    }
}
