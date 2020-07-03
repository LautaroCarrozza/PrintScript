package statement.parsers;

import expression.Expression;
import statement.Statement;
import statement.impl.ExpressionStatement;
import streams.TokenStream;

import static lexer.token.TokenType.SEMICOLON;
import static utils.ParserHelper.consume;

public class ExpressionStatementParser extends AbstractStatementParser{

    @Override
    public Statement parse(TokenStream tokenStream) {
        Expression expr = expressionParser.parse(tokenStream);

        consume(SEMICOLON, "Expect ';' after expression", tokenStream);
        return new ExpressionStatement(expr);
    }

}
