package expression.parsers;

import exception.ParserException;
import expression.Expression;
import expression.impl.*;
import lexer.token.Token;
import lexer.token.TokenType;
import streams.TokenStream;

import java.util.Arrays;
import java.util.List;

import static lexer.token.TokenType.*;
import static lexer.token.TokenType.RIGHT_PAREN;
import static utils.ParserHelper.checkType;
import static utils.ParserHelper.consume;

public class ExpressionParserImpl implements ExpressionParser {

    /*
        expr -> assignment
        assignment -> ID "=" assignment | equality
        equality -> comparison

     */

    private final List<TokenType> comparisonTypes = Arrays.asList(GREATER, GREATER_EQUALS, LESS, LESS_EQUALS);

    @Override
    public Expression parse(TokenStream tokenStream) {

        Expression expr = equality(tokenStream);

        if (checkType(tokenStream.peek(), ASSIGNATION)) {
            throw new ParserException("Invalid assignment target", tokenStream.peek());
        }

        return expr;
    }

    private Expression equality(TokenStream tokenStream) throws ParserException {
        Expression expr = comparison(tokenStream);

        while (checkType(tokenStream.peek(), EQUALS)) {
            Token operator = tokenStream.peekAdvance();

            Expression right = comparison(tokenStream);

            //left equals right
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;

    }

    private Expression comparison(TokenStream tokenStream) throws ParserException {
        //left expr
        Expression expr = addition(tokenStream);

        while (comparisonTypes.contains(tokenStream.peek().getType())) {
            Token operator = tokenStream.peekAdvance();

            //right part expression
            Expression right = addition(tokenStream);

            //left expr - comp operator token - right expr
            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression addition(TokenStream tokenStream) throws ParserException {
        Expression expr = multiplication(tokenStream);

        while (checkType(tokenStream.peek(), MINUS) || checkType(tokenStream.peek(), PLUS)) {
            Token operator = tokenStream.peekAdvance();

            Expression right = multiplication(tokenStream);

            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression multiplication(TokenStream tokenStream) throws ParserException {
        Expression expr = unary(tokenStream);

        while (checkType(tokenStream.peek(), DIVIDE) || checkType(tokenStream.peek(), MULTIPLY)) {
            Token operator = tokenStream.peekAdvance();

            Expression right = unary(tokenStream);

            expr = new BinaryExpression(expr, operator, right);
        }

        return expr;
    }

    private Expression unary(TokenStream tokenStream) throws ParserException {

        //Se podria agregar para not tambien
        if (checkType(tokenStream.peek(), MINUS)) {
            Token operator = tokenStream.peekAdvance();

            Expression right = unary(tokenStream);

            return new UnaryExpression(operator, right);
        }

        return primary(tokenStream);
    }

    private Expression primary(TokenStream tokenStream) throws ParserException {

        if (checkType(tokenStream.peek(), FALSE)){
            tokenStream.advance();
            return new LiteralExpression(false, LiteralType.BOOLEAN);
        }

        if (checkType(tokenStream.peek(), TRUE)){
            tokenStream.advance();
            return new LiteralExpression(true, LiteralType.BOOLEAN);
        }

        if (checkType(tokenStream.peek(), NUMBER) || checkType(tokenStream.peek(), STRING)) {
            Token t = tokenStream.peekAdvance();
            if (t.getType() == NUMBER) return new LiteralExpression(Double.valueOf(t.getValue()), LiteralType.NUMBER);

            return new LiteralExpression(t.getValue(), LiteralType.STRING);
        }

        if (checkType(tokenStream.peek(), IDENTIFIER)) {
            Token t = tokenStream.peekAdvance();

            return variableOrAssignmentExpression(t, tokenStream);
        }

        if (checkType(tokenStream.peek(), LEFT_PAREN)) {
            tokenStream.advance();
            Expression expr = parse(tokenStream);
            consume(RIGHT_PAREN, "Expect ')' after expression.", tokenStream);
            return new GroupedExpression(expr);
        }

        throw new ParserException("Expect expression.", tokenStream.peek());
    }

    private Expression variableOrAssignmentExpression(Token t, TokenStream tokenStream) {
        if (checkType(tokenStream.peek(), ASSIGNATION)) {
            tokenStream.advance();
            Expression value = parse(tokenStream);

            return new AssignmentExpression(t, value);
        }
        return new VariableExpression(t);
    }


}
