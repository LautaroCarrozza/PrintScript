package lexer;

import lexer.exception.IllegalInputException;
import lexer.state.LexerContext;
import lexer.state.SingleCharacterState;
import lexer.streams.TextStream;
import lexer.token.Token;
import lexer.token.TokenType;

import java.util.ArrayList;
import java.util.List;

import static lexer.token.TokenType.INVALID;

public class LexerImpl implements Lexer {

    private LexerState lexerState;

    public LexerImpl() {
        this.lexerState = new SingleCharacterState(new LexerContext());
    }

    @Override
    public List<Token> lex(TextStream source) {

        List<Token> tokens = new ArrayList<>();

        while(source.hasNext()) {
            Character c = source.peek();

            lexerState = lexerState.next(c);

            if (lexerState.hasToken()){
                validateToken(lexerState.getToken());
                tokens.add(lexerState.getToken());
            }

            if (lexerState.consumed())
                source.advance();

        }
        return tokens;
    }

    private void validateToken(Token token) {
        //todo error Reporter

        if (token.getType().equals(INVALID))
            throw new IllegalInputException(" at position: ("
                    + token.getInputRange().getStartLine() + ","
                    + token.getInputRange().getStartColumn() + ")-("
                    + token.getInputRange().getCurrentLine() + ","
                    + token.getInputRange().getCurrentColumn() + ")"
            );
    }
}
