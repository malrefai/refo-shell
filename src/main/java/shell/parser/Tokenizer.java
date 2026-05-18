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
        boolean isEscaped = false;
        boolean isDoubleQuoteEscaped = false;
        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;

        for (char c : input.toCharArray()) {
            if (isEscaped) {
                state.currentToken.append(c);
                isEscaped = false;
            } else if (inSingleQuotes) {
                inSingleQuotes = inQuotes(state, '\'', c);
            } else if (inDoubleQuotes) {
                if (isDoubleQuoteEscaped) {
                    isDoubleQuoteEscaped = false;
                    if (c == '"' || c == '\\') {
                        // Valid escape sequence, preserve the escape character
                        state.currentToken.append(c);
                    } else {
                        // Invalid escape: preserve the backslash AND append the character
                        state.currentToken.append('\\').append(c);
                    }
                } else if (c == '\\') {
                    isDoubleQuoteEscaped = true; // Turn on lookahead flag for the next iteration
                } else {
                    inDoubleQuotes = inQuotes(state, '"', c);
                }
            } else { // Unquoted text
                if (c =='\\') {
                    isEscaped = true; // Escaped character
                } else if ( c == '\'') {
                    inSingleQuotes = true; // Start of single-quoted string
                } else if (c == '"') {
                    inDoubleQuotes = true; // Start of double-quoted string
                } else if (Character.isWhitespace(c)) {
                    state.flush(); // Whitespace acts as a delimiter only when unquoted
                } else {
                    state.currentToken.append(c); // Normal character
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
