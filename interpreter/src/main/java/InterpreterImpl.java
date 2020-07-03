import exception.InterpreterException;
import statement.Statement;
import visitor.Visitor;

import java.util.List;

public class InterpreterImpl implements Interpreter {

    @Override
    public void interpret(List<Statement> statements) throws InterpreterException {
        Visitor visitor = new Visitor();
        for (Statement statement : statements) {
            statement.accept(visitor);
        }
    }
}
