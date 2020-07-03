import exception.ParserException;
import lexer.token.Token;
import statement.Statement;

import java.util.List;

public interface Parser {

    List<Statement> parse(List<Token> tokens) throws ParserException;

}
