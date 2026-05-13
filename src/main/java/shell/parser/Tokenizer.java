package shell.parser;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    // state container
    private static class TokenState {
        final List<String> tokens = new ArrayList<>();
        final StringBuilder currentToken = new StringBuilder();
        boolean hasContent = false;

        void flush() {
            if (!currentToken.isEmpty() || hasContent) {
                tokens.add(currentToken.toString());
                currentToken.setLength(0);
                hasContent = false;
            }
        }
    }

    public static List<String> tokenize(String input) {
        if (input  == null || input.isBlank()) return List.of();

        TokenState state = new TokenState();
        boolean inSingleQuotes = false;

        for (char c : input.toCharArray()) {
            if (inSingleQuotes) {
                if (c == '\'') {
                    inSingleQuotes = false;
                    state.hasContent = true;
                } else {
                    state.currentToken.append(c);
                }
            } else { // Unquoted text
                if ( c == '\'') {
                    inSingleQuotes = true;
                } else if (Character.isWhitespace(c)) {
                    state.flush(); // Clean and readable
                } else {
                    // Regular character outside quotes
                    state.currentToken.append(c);
                }
            }
        }

        state.flush(); // Catch the final token if the string doesn't end with a space
        return state.tokens;
    }
}
