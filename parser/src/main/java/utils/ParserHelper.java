package utils;

import exception.ParserException;
import lexer.token.Token;
import lexer.token.TokenType;
import streams.TokenStream;

public class ParserHelper {

    public static Token consume(TokenType type, String message, TokenStream tokenStream) throws ParserException {
        Token token = tokenStream.peek();

        if (tokenStream.hasNext() && token.getType() == type) {
            tokenStream.advance();
            return token;
        }

        throw new ParserException(message, token);
    }

    public static boolean checkType(Token token, TokenType tokenType) {
        return token.getType() == tokenType;
    }
}
