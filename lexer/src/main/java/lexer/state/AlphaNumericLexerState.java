package lexer.state;

import lexer.LexerState;
import lexer.token.TokenType;

public class AlphaNumericLexerState extends AbstractLexerState {

    public AlphaNumericLexerState(LexerContext lexerContext) {
        super(lexerContext);
    }

    @Override
    public LexerState next(Character c) {

        if (delimiters.contains(c)){
            InputRange nInput = lexerContext.getInputRange().newPointFromCurrent();

            return new HasTokenState(new LexerContext("", nInput),
                    new AlphaNumericLexerState(new LexerContext(lexerContext.getAccum(), lexerContext.moveBack())), false);
        }

        return new AlphaNumericLexerState(new LexerContext(lexerContext.getAccum() + c, lexerContext.updateInputRange(c)));
    }

    @Override
    protected TokenType getTokenType() {
        switch (lexerContext.getAccum()){
            case "if":
                return TokenType.IF;
            case "else":
                return TokenType.ELSE;
            case "print":
                return TokenType.PRINT;
            case "let":
                return TokenType.LET;
            case "const":
                return TokenType.CONST;
            case "string":
                return TokenType.STRING;
            case "number":
                return TokenType.NUMBER;
            case "boolean":
                return TokenType.BOOLEAN;
            case "true":
                return TokenType.TRUE;
            case "false":
                return TokenType.FALSE;
            default:
                return TokenType.IDENTIFIER;
        }
    }

}
