package environment;

import exception.InterpreterException;
import lexer.token.Token;
import lexer.token.TokenType;

import java.util.Map;

public interface Environment {
    Map<String, Declaration> getValues();
    void addValue(String name, TokenType keyword, TokenType type, Object value);
    void assign(Token name, Object value);
    Object getValue(Token name) throws InterpreterException;
    Environment getEnclosing();
}
