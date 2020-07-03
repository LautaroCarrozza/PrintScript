package statement.parsers;

import exception.ParserException;
import statement.Statement;
import statement.impl.BlockStatement;
import streams.TokenStream;

import java.util.ArrayList;
import java.util.List;

import static lexer.token.TokenType.RIGHT_BRACE;
import static utils.ParserHelper.checkType;
import static utils.ParserHelper.consume;

public class BlockStatementParser extends AbstractStatementParser {

    public Statement parse(TokenStream tokenStream) {
        return new BlockStatement(block(tokenStream));
    }

    private List<Statement> block(TokenStream tokenStream) throws ParserException {
        List<Statement> statements = new ArrayList<>();

        while (!checkType(tokenStream.peek(), RIGHT_BRACE) && tokenStream.hasNext()) {
            //statements from start and ends with }
            statements.add(new DeclarationStatementParser().parse(tokenStream));
        }

        consume(RIGHT_BRACE, "Expect '}' after block", tokenStream);
        return statements;
    }
}
