package exception;

import lexer.token.Token;

public class ParserException extends RuntimeException {

    private String message;
    private Token token;

    public ParserException(String message, Token token) {
        this.message = message;
        this.token = token;
    }

    @Override
    public String getMessage() {
        return message + " at position: ("
                + token.getInputRange().getStartLine() + ","
                + token.getInputRange().getStartColumn() + ")-("
                + token.getInputRange().getCurrentLine() + ","
                + token.getInputRange().getCurrentColumn() + ")";
    }

    public Token getToken() {
        return token;
    }
}
