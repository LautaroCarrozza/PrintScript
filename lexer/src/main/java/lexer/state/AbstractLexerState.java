package lexer.state;

import lexer.LexerState;
import lexer.token.Token;
import lexer.token.TokenType;

import java.util.Arrays;
import java.util.List;

abstract class AbstractLexerState implements LexerState {

    LexerContext lexerContext;
    boolean consumedChar = true;
    final List<Character> delimiters = Arrays.asList(' ', ':', ';', '=', '+', '-', '*', '/', '>', '<', '"', '\'', '.', ',', '(', ')', '}', '{');
    final List<Character> symbols = Arrays.asList(':', ';', '=', '+', '-', '*', '/', '>', '<', '(', ')', '}', '{');


    public AbstractLexerState(LexerContext lexerContext) {
        this.lexerContext = lexerContext;
    }

    @Override
    public boolean hasToken() {
        return false;
    }

    @Override
    public boolean consumed() {
        return this.consumedChar;
    }

    @Override
    public Token getToken() {
        return new Token(getTokenType(), lexerContext.getAccum(), lexerContext.getInputRange());
    }

    LexerState defaultCase (Character c){

        switch (c) {
            case '"':
            case '\'': return new StringLexerState(new LexerContext("" + c, lexerContext.resetRange()));

            case ' ' :
            case '\r':
            case '\t':
            case '\n':
                return new SingleCharacterState(new LexerContext("", lexerContext.updateInputRange(c)));

            case '=' :
            case '<' :
            case '>' : return new ComparatorLexerState(new LexerContext(c.toString(), lexerContext.updateInputRange(c)));

            default:
                if (symbols.contains(c)){
                    LexerContext newContext = new LexerContext("", lexerContext.resetRange());
                    return new HasTokenState(newContext, new SingleCharacterState(new LexerContext("" + c, lexerContext.getInputRange())), true);
                }
                else if (isDigit(c)){
                    return new NumberLexerState(new LexerContext("" + c, lexerContext.updateInputRange(c)));
                }
                else if (isAlpha(c)){
                    return new AlphaNumericLexerState(new LexerContext("" + c, lexerContext.updateInputRange(c)));
                }

                return new InvalidInputState(new LexerContext(lexerContext.getAccum(), lexerContext.getInputRange()));
        }
    }

    protected abstract TokenType getTokenType();

    private boolean isAlpha(Character c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isDigit(Character c) {
        return c >= '0' && c <= '9';
    }
}
