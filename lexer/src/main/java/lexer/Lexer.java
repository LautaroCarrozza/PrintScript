package lexer;

import lexer.streams.TextStream;
import lexer.token.Token;

import java.util.List;

public interface Lexer {

    List<Token> lex(TextStream source);
}
