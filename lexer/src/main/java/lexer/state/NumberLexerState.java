package lexer.state;

import lexer.LexerState;
import lexer.token.TokenType;

public class NumberLexerState extends AbstractLexerState {

    public NumberLexerState(LexerContext lexerContext) {
        super(lexerContext);
    }

    @Override
    public LexerState next(Character c) {

        String accum = lexerContext.getAccum() + c;
        //if dot
        if (c.equals('.')){
            if (lexerContext.getAccum().contains(".")) return new InvalidInputState(new LexerContext(accum, lexerContext.getInputRange()));

            return new NumberLexerState(new LexerContext(accum, lexerContext.updateInputRange(c)));
        }

        //if delimiter
        if(delimiters.contains(c)){

            if (lexerContext.getAccum().endsWith(".")){
                return new InvalidInputState(new LexerContext(accum, lexerContext.getInputRange()));
            }
            InputRange nInput = lexerContext.getInputRange().newPointFromCurrent();

            return new HasTokenState(new LexerContext("", nInput),
                    new NumberLexerState(new LexerContext(lexerContext.getAccum(), lexerContext.moveBack())), false);
        }

        //if number
        return new NumberLexerState(new LexerContext(lexerContext.getAccum() + c, lexerContext.updateInputRange(c)));
    }

    @Override
    protected TokenType getTokenType() {
        return TokenType.NUMBER;
    }
}
