package exception;

import lexer.token.Token;

public class InterpreterException extends RuntimeException {
    private Token token;

    public InterpreterException(Token token, String message) {
        super(message);
        this.token = token;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + " at position: ("
                + token.getInputRange().getStartLine() + ","
                + token.getInputRange().getStartColumn() + ")-("
                + token.getInputRange().getCurrentLine() + ","
                + token.getInputRange().getCurrentColumn();
    }
}
