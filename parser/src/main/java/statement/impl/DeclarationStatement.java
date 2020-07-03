package statement.impl;

import expression.Expression;
import lexer.token.Token;
import lexer.token.TokenType;
import statement.Statement;
import visitor.StatementVisitor;

public class DeclarationStatement implements Statement {

    //let - const
    private Token keyword;
    //variable name
    private Token name;
    //var type
    private TokenType type;

    private Expression initializer;

    public DeclarationStatement(Token keyword, Token name, TokenType type, Expression initializer) {
        this.keyword = keyword;
        this.name = name;
        this.type = type;
        this.initializer = initializer;
    }

    public Token getKeyword() {
        return keyword;
    }

    public Token getName() {
        return name;
    }

    public TokenType getType() {
        return type;
    }

    public Expression getInitializer() {
        return initializer;
    }

    @Override
    public Expression getExpression() {
        return initializer;
    }

    @Override
    public void accept(StatementVisitor visitor) {
        visitor.visit(this);
    }
}
