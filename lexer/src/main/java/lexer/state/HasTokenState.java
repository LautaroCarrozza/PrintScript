package lexer.state;

import lexer.LexerState;
import lexer.token.Token;
import lexer.token.TokenType;

public class HasTokenState extends AbstractLexerState {

    private AbstractLexerState previousLexerState;

    public HasTokenState(LexerContext lexerContext, AbstractLexerState previousLexerState, boolean consumed) {
        super(lexerContext);
        this.previousLexerState = previousLexerState;
        consumedChar = consumed;
    }

    @Override
    public LexerState next(Character c) {
        return defaultCase(c);
    }

    @Override
    public Token getToken() {
        return new Token(getTokenType(), previousLexerState.lexerContext.getAccum(), previousLexerState.lexerContext.getInputRange());
    }

    @Override
    protected TokenType getTokenType() {
        return previousLexerState.getToken().getType();
    }

    @Override
    public boolean hasToken() {
        return true;
    }
}
