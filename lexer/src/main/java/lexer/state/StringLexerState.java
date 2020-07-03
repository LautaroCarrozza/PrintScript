package lexer.state;

import lexer.LexerState;
import lexer.token.TokenType;

public class StringLexerState extends AbstractLexerState {

    public StringLexerState(LexerContext lexerContext) {
        super(lexerContext);
    }

    @Override
    public LexerState next(Character c) {

        if ((lexerContext.getAccum().startsWith("\"") && c.equals('"'))
                ||
            (lexerContext.getAccum().startsWith("'") && c.equals('\''))){
            //saco comillas
            String accum = lexerContext.getAccum().substring(1);
            return new HasTokenState(new LexerContext("", lexerContext.resetRange()), new StringLexerState(new LexerContext(accum, lexerContext.moveBack())), true);
        }

        return new StringLexerState(new LexerContext(lexerContext.getAccum() + c, lexerContext.updateInputRange(c)));
    }


    @Override
    protected TokenType getTokenType() {
        return TokenType.STRING;
    }

}
