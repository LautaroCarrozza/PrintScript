package lexer.streams;

public class TextStreamImpl implements TextStream {

    private String text;
    private int position;

    public TextStreamImpl(String text) {
        this.text = text;
        this.position = 0;
    }

    @Override
    public char peek() {
        return text.charAt(position);
    }

    @Override
    public void advance() {
        position ++;
    }

    @Override
    public boolean hasNext() {
        return position < text.length();
    }
}
