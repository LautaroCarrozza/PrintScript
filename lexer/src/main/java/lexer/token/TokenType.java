package lexer.token;

public enum TokenType {

    // Single-character tokens.
    LEFT_PAREN,
    RIGHT_PAREN,
    LEFT_BRACE,
    RIGHT_BRACE,
    SEMICOLON,
    COLON,
    MINUS,
    PLUS,
    MULTIPLY,
    DIVIDE,

    //Comparison
    EQUALS,
    GREATER,
    GREATER_EQUALS,
    LESS,
    LESS_EQUALS,
    ASSIGNATION,

    // Literals.
    IDENTIFIER,
    STRING,
    NUMBER,
    BOOLEAN,
    TRUE,
    FALSE,

    // Keywords.
    IF,
    ELSE,
    PRINT,
    LET,
    CONST,

    EOF,

    INVALID
}
