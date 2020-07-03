package expression.parsers;

import expression.Expression;
import streams.TokenStream;

public interface ExpressionParser {

    Expression parse(TokenStream tokenStream);
}
