package statement.parsers;

import expression.Expression;
import statement.Statement;
import statement.impl.IfStatement;
import streams.TokenStream;

import static lexer.token.TokenType.*;
import static utils.ParserHelper.checkType;
import static utils.ParserHelper.consume;

public class IfStatementParser extends AbstractStatementParser{


    @Override
    public Statement parse(TokenStream tokenStream) {
        consume(LEFT_PAREN, "Expect '(' after 'if'", tokenStream);

        Expression condition = expressionParser.parse(tokenStream);

        consume(RIGHT_PAREN, "Expect ')' after if condition", tokenStream);

        Statement thenBranch = super.parse(tokenStream);

        Statement elseBranch = null;

        if (checkType(tokenStream.peek(), ELSE)) {
            tokenStream.advance();
            elseBranch = super.parse(tokenStream);
        }

        return new IfStatement(condition, thenBranch, elseBranch);
    }
}
