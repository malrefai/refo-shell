package shell.parser;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private Tokenizer() {
        /* This utility class should not be instantiated */
    }

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
        boolean inDoubleQuotes = false;

        for (char c : input.toCharArray()) {
            if (inSingleQuotes) {
                inSingleQuotes = inQuotes(state, '\'', c);
            } else if (inDoubleQuotes) {
                inDoubleQuotes = inQuotes(state, '"', c);
            } else { // Unquoted text
                if ( c == '\'') {
                    inSingleQuotes = true;
                } else if (c == '"') {
                    inDoubleQuotes = true;
                } else if (Character.isWhitespace(c)) {
                    state.flush(); // Whitespace acts as a delimiter only when unquoted
                } else {
                    // Regular character outside quotes
                    state.currentToken.append(c);
                }
            }
        }

        state.flush(); // Catch the final token if the string doesn't end with a space
        return state.tokens;
    }

    private static boolean inQuotes(TokenState state, char quote, char character) {
        if (character == quote) {
            state.hasContent = true; // Remembers explicit content (handles '' or "")
            return false;
        } else {
            state.currentToken.append(character);
            return true;
        }
    }
}
