package environment.impl;

import environment.Declaration;
import environment.Environment;
import exception.InterpreterException;
import lexer.token.Token;
import lexer.token.TokenType;

import java.util.HashMap;
import java.util.Map;

import static lexer.token.TokenType.*;

public class EnvironmentImpl implements Environment {

    private Map<String, Declaration> values;
    private Environment enclosing;

    public EnvironmentImpl() {
        this.values = new HashMap<>();
        enclosing = null;
    }

    public EnvironmentImpl(Environment enclosing) {
        this.values = new HashMap<>();
        this.enclosing = enclosing;
    }

    @Override
    public Map<String, Declaration> getValues() {
        return values;
    }

    @Override
    public void addValue(String name, TokenType keyword, TokenType type, Object value) {
        values.put(name, new DeclarationImpl(keyword, type, value));
    }

    @Override
    public void assign(Token name, Object value) {
        if (values.containsKey(name.getValue())) {
            Declaration declaration = values.get(name.getValue());
            if(declaration.getKeyword() == LET){
                if (declaration.getType() == BOOLEAN){
                    if (!(value instanceof Boolean)){
                        throw new InterpreterException(name, "Expected a boolean");
                    }
                }
                else if (declaration.getType() == NUMBER){
                    if (!(value instanceof Number)) {
                        throw new InterpreterException(name, "Expected a number");
                    }
                }
                else if (declaration.getType() == STRING){
                    if (!(value instanceof String)){
                        throw new InterpreterException(name, "Expected a string");
                    }
                }
                declaration.setValue(value);
                values.put(name.getValue(), declaration);
                return;
            } else {
                throw new InterpreterException(name, "Constant cannot be changed");
            }
        }
        if (enclosing != null) {
            enclosing.assign(name, value);
            return;
        }
        throw new InterpreterException(name, "Undefined variable '" + name.getValue() + "'.");
    }

    @Override
    public Object getValue(Token name) throws InterpreterException {
        if (values.containsKey(name.getValue())) {
            return values.get(name.getValue()).getValue();
        }
        if (enclosing != null) return enclosing.getValue(name);

        throw new InterpreterException(name, "Undefined variable '" + name.getValue() + "'.");
    }

    @Override
    public Environment getEnclosing() {
        return enclosing;
    }
}
