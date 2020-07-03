package streams;

import lexer.token.Token;

import java.util.List;

public class TokenStreamImpl implements TokenStream {

    private final List<Token> tokens;
    private int position;

    public TokenStreamImpl(List<Token> tokens) {
        this.tokens = tokens;
        this.position = 0;
    }

    @Override
    public Token peek() {
        if (!hasNext()) return tokens.get(position -1);
        return tokens.get(position);
    }

    @Override
    public Token peekAdvance() {
        Token t = peek();
        advance();
        return t;
    }

    @Override
    public void advance() {
        position ++;
    }

    @Override
    public boolean hasNext() {
        return position < tokens.size();
    }
}
