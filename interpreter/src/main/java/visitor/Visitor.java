package visitor;

import environment.impl.EnvironmentImpl;
import environment.*;
import exception.InterpreterException;
import expression.Expression;
import expression.impl.*;
import lexer.token.Token;
import statement.Statement;
import statement.impl.*;

import java.util.List;

import static lexer.token.TokenType.*;

public class Visitor implements ExpressionVisitor, StatementVisitor{

    private Environment environment = new EnvironmentImpl();

    @Override
    public Object visit(BinaryExpression binaryExpression) {
        Object left = evaluate(binaryExpression.getLeft());
        Object right = evaluate(binaryExpression.getRight());

        switch (binaryExpression.getOperand().getType()) {
            case EQUALS:
                return isEqual(left, right);
            case GREATER:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left > (double)right;
            case GREATER_EQUALS:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left >= (double)right;
            case LESS:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left < (double)right;
            case LESS_EQUALS:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left <= (double)right;
            case MINUS:
                return (double)left - (double)right;
            case PLUS:
                if(left instanceof Number && right instanceof Number){
                    return (double)left + (double)right;
                }
                return left.toString() + right.toString();
            case DIVIDE:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left / (double)right;
            case MULTIPLY:
                checkNumberOperands(binaryExpression.getOperand(), left, right);
                return (double)left * (double)right;
        }
        return null;
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) return true;
        if (a == null) return false;

        return a.equals(b);
    }

    private void checkNumberOperands(Token operator, Object left, Object right) {
        if (left instanceof Double && right instanceof Double) return;
        throw new InterpreterException(operator, "Operands must be numbers");
    }

    private void checkNumberOperands(Token operator, Object object){
        if(object instanceof Double) return;
        throw new InterpreterException(operator, "Operand must be a number");
    }

    private Object evaluate(Expression expression) {
        return expression.accept(this);
    }

    @Override
    public Object visit(UnaryExpression unaryExpression) {
        Object right = evaluate(unaryExpression.getExpression());

        if (unaryExpression.getOperand().getType() == MINUS) {
            checkNumberOperands(unaryExpression.getOperand(), right);
            return -(double) right;
        }
        return null;
    }

    @Override
    public Object visit(GroupedExpression groupedExpression) {
        return evaluate(groupedExpression.getExpression());

    }

    @Override
    public Object visit(LiteralExpression literalExpression) {
        return literalExpression.getValue();
    }

    @Override
    public Object visit(VariableExpression variableExpression) {
        return environment.getValue(variableExpression.getName());
    }

    @Override
    public Object visit(AssignmentExpression assignmentExpression) {
        Object value = evaluate(assignmentExpression.getValue());

        environment.assign(assignmentExpression.getName(), value);
        return value;
    }

    @Override
    public void visit(PrintStatement printStatement) {
        Object value = evaluate(printStatement.getExpression());
        System.out.println(value);
    }

    @Override
    public void visit(ExpressionStatement expressionStatement) {
        expressionStatement.getExpression().accept(this);
    }

    @Override
    public void visit(DeclarationStatement declarationStatement) {
        Object value = null;

        //Si fue inicializada..
        if (declarationStatement.getInitializer() != null) {
            value = evaluate(declarationStatement.getInitializer());
        }

        if(value == null){
            environment.addValue(declarationStatement.getName().getValue(), declarationStatement.getKeyword().getType(), declarationStatement.getType(), null);
        }

        else if (declarationStatement.getType() == BOOLEAN){
            if (!(value instanceof Boolean)){
                throw new InterpreterException(declarationStatement.getName(), "Expected a boolean");
            }
        }

        else if (declarationStatement.getType() == NUMBER){
            if (!(value instanceof Double)){
                throw new InterpreterException(declarationStatement.getName(), "Expected a number");
            }
        }

        else if (declarationStatement.getType() == STRING){
            if (!(value instanceof String)){
                throw new InterpreterException(declarationStatement.getName(), "Expected a string");
            }
        }
        environment.addValue(declarationStatement.getName().getValue(), declarationStatement.getKeyword().getType(), declarationStatement.getType(), value);
    }

    @Override
    public void visit(BlockStatement blockStatement) {
        executeBlock(blockStatement.getStatements(), new EnvironmentImpl(environment));
    }

    private void executeBlock(List<Statement> statements, Environment environment) {
        Environment previous = this.environment;
        try {
            this.environment = environment;

            for (Statement statement : statements) {
                statement.accept(this);
            }
        } finally {
            this.environment = previous;
        }
    }

    @Override
    public void visit(IfStatement ifStatement) {
        if (isTruthy(evaluate(ifStatement.getCondition()))) {
            ifStatement.getThenStatement().accept(this);
        } else if (ifStatement.getElseStatement() != null) {
            ifStatement.getElseStatement().accept(this);
        }
    }

    private boolean isTruthy(Object object) {
        if (object == null) return false;
        if (object instanceof Boolean) return (boolean)object;
        return true;
    }
}
