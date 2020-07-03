package lexer.token;

import lexer.state.InputRange;

public class Token {

    private final TokenType type;
    private final String value;
    private final InputRange inputRange;

    public Token(TokenType type, String value, InputRange inputRange) {
        this.type = type;
        this.value = value;
        this.inputRange = inputRange;
    }


    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public InputRange getInputRange() {
        return inputRange;
    }
}
