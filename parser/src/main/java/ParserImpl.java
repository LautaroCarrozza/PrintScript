import exception.ParserException;
import lexer.token.Token;
import statement.Statement;
import statement.parsers.DeclarationStatementParser;
import statement.parsers.StatementParser;
import streams.TokenStream;
import streams.TokenStreamImpl;

import java.util.ArrayList;
import java.util.List;


public class ParserImpl implements Parser {

    private StatementParser statementParser;

    public ParserImpl() {
        this.statementParser = new DeclarationStatementParser();
    }

    @Override
    public List<Statement> parse(List<Token> tokens) throws ParserException {

        TokenStream tokenStream = new TokenStreamImpl(tokens);

        List<Statement> statements = new ArrayList<>();

        while (tokenStream.hasNext()){
            statements.add(statementParser.parse(tokenStream));
        }

        return statements;
    }
}
