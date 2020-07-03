package lexer.state;

import lexer.LexerState;
import lexer.token.TokenType;

public class InvalidInputState extends AbstractLexerState {

    public InvalidInputState(LexerContext lexerContext) {
        super(lexerContext);
    }

    @Override
    protected TokenType getTokenType() {
        return TokenType.INVALID;
    }

    @Override
    public LexerState next(Character c) {
        return defaultCase(c);
    }

    @Override
    public boolean hasToken() {
        return true;
    }

}
