package statement.parsers;

import statement.Statement;
import streams.TokenStream;

public interface StatementParser {

    Statement parse(TokenStream tokenStream);

}
