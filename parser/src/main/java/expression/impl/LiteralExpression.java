package expression.impl;

import expression.Expression;
import expression.parsers.LiteralType;
import visitor.ExpressionVisitor;

public class LiteralExpression implements Expression {

    //String or number
    private LiteralType type;
    private Object value;

    public LiteralExpression(Object value, LiteralType type) {
        this.value = value;
        this.type = type;
    }

    public LiteralType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public Object accept(ExpressionVisitor visitor) {
        return visitor.visit(this);
    }
}
