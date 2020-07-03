package lexer.streams;

public interface TextStream {

    char peek();

    void advance();

    boolean hasNext();

}
