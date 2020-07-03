package statement.parsers;

import expression.Expression;
import statement.Statement;
import statement.impl.PrintStatement;
import streams.TokenStream;

import static lexer.token.TokenType.SEMICOLON;
import static utils.ParserHelper.consume;

public class PrintStatementParser extends AbstractStatementParser{

    @Override
    public Statement parse(TokenStream tokenStream) {
        Expression value = expressionParser.parse(tokenStream);
        consume(SEMICOLON, "Expect ';' after value", tokenStream);
        return new PrintStatement(value);
    }
}
