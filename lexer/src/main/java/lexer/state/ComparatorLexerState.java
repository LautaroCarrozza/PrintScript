package lexer.state;

import lexer.LexerState;
import lexer.token.TokenType;

public class ComparatorLexerState extends AbstractLexerState {

    public ComparatorLexerState(LexerContext lexerContext) {
        super(lexerContext);
    }

    @Override
    protected TokenType getTokenType() {
        switch (lexerContext.getAccum()) {
            case "<": return TokenType.LESS;
            case ">": return TokenType.GREATER;
            case "=": return TokenType.ASSIGNATION;
            case "<=": return TokenType.LESS_EQUALS;
            case ">=": return TokenType.GREATER_EQUALS;
            case "==": return TokenType.EQUALS;
            default:
                return TokenType.INVALID;
        }
    }

    @Override
    public LexerState next(Character c) {
        String accum = lexerContext.getAccum() + c;

        if (accum.length() > 2) return new InvalidInputState(lexerContext);

        if (c.equals('=')) {
            return new HasTokenState(new LexerContext("", lexerContext.resetRange()),
                    new ComparatorLexerState(new LexerContext(accum, lexerContext.updateInputRange(c))), true);
        }

        else {
            return new HasTokenState(new LexerContext("", lexerContext.getInputRange()),
                    new ComparatorLexerState(new LexerContext(lexerContext.getAccum(), lexerContext.moveBack())), false);
        }
    }
}
