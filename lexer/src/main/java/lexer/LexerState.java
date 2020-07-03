package lexer;

import lexer.token.Token;

public interface LexerState {

    LexerState next(Character c);
    Token getToken();
    boolean hasToken();
    boolean consumed();
}
