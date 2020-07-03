package streams;

import lexer.token.Token;

public interface TokenStream {

    Token peek();

    Token peekAdvance();

    void advance();

    boolean hasNext();
}
