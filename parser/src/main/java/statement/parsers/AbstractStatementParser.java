package statement.parsers;

import expression.parsers.ExpressionParser;
import expression.parsers.ExpressionParserImpl;
import statement.Statement;
import streams.TokenStream;

import static lexer.token.TokenType.*;
import static utils.ParserHelper.checkType;

public class AbstractStatementParser implements StatementParser {

    ExpressionParser expressionParser;

    public AbstractStatementParser() {
        this.expressionParser = new ExpressionParserImpl();
    }

    @Override
    public Statement parse(TokenStream tokenStream) {

        if (checkType(tokenStream.peek(), IF)) {
            tokenStream.advance();
            return new IfStatementParser().parse(tokenStream);
        }
        if (checkType(tokenStream.peek(), PRINT)) {
            tokenStream.advance();
            return new PrintStatementParser().parse(tokenStream);
        }
        if (checkType(tokenStream.peek(), LEFT_BRACE)) {
            tokenStream.advance();
            return new BlockStatementParser().parse(tokenStream);
        }

        return new ExpressionStatementParser().parse(tokenStream);

    }


}
