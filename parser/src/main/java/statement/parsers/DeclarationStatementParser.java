package statement.parsers;

import exception.ParserException;
import expression.Expression;
import lexer.token.Token;
import lexer.token.TokenType;
import statement.Statement;
import statement.impl.DeclarationStatement;
import streams.TokenStream;

import static lexer.token.TokenType.*;
import static lexer.token.TokenType.SEMICOLON;
import static utils.ParserHelper.checkType;
import static utils.ParserHelper.consume;

public class DeclarationStatementParser extends AbstractStatementParser {


    @Override
    public Statement parse(TokenStream tokenStream) {
        Token currentToken = tokenStream.peek();

        switch (currentToken.getType()) {
            case CONST:
            case LET: {
                tokenStream.advance();
                return declarationStatement(currentToken, tokenStream);
            }

        }

        return super.parse(tokenStream);
    }

    private Statement declarationStatement(Token currentToken, TokenStream tokenStream) throws ParserException {

        Token name = consume(IDENTIFIER, "Expect variable name", tokenStream);

        TokenType type;
        Expression initializer = null;

        if (checkType(tokenStream.peek(), COLON)){
            tokenStream.advance();
            type = getVariableType(tokenStream);

        } else throw new ParserException("Expected type declaration", tokenStream.peek());


        if (checkType(tokenStream.peek(), ASSIGNATION)) {
            tokenStream.advance();
            initializer = expressionParser.parse(tokenStream);
        }

        //throws error if doesnt match
        consume(SEMICOLON, "Expect ';' after variable declaration", tokenStream);
        return new DeclarationStatement(currentToken, name, type, initializer);
    }

    private TokenType getVariableType(TokenStream tokenStream) {
        if (checkType(tokenStream.peek(), NUMBER)){
            tokenStream.advance();
            return NUMBER;
        }
        else if (checkType(tokenStream.peek(), STRING)){
            tokenStream.advance();
            return STRING;
        }
        else if (checkType(tokenStream.peek(), BOOLEAN)){
            tokenStream.advance();
            return BOOLEAN;
        }
        else throw new ParserException("Unexpected type", tokenStream.peek());
    }
}
