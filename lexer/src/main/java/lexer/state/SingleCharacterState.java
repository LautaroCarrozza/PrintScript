package lexer.state;

import lexer.LexerState;
import lexer.token.Token;
import lexer.token.TokenType;

import java.util.Arrays;
import java.util.List;

public class SingleCharacterState extends AbstractLexerState {


    public SingleCharacterState(LexerContext lexerContext) {
        super(lexerContext);
    }

    @Override
    protected TokenType getTokenType() {
        switch (lexerContext.getAccum()) {
            case "(": return TokenType.LEFT_PAREN;
            case ")": return TokenType.RIGHT_PAREN;
            case "{": return TokenType.LEFT_BRACE;
            case "}": return TokenType.RIGHT_BRACE;
            case "-": return TokenType.MINUS;
            case "+": return TokenType.PLUS;
            case ";": return TokenType.SEMICOLON;
            case ":": return TokenType.COLON;
            case "*": return TokenType.MULTIPLY;
            case "/": return TokenType.DIVIDE;
            default:
                return TokenType.INVALID;
        }
    }


    @Override
    public LexerState next(Character c) {
        return defaultCase(c);
    }


}
