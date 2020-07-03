package lexer.state;

public class InputRange {

    private int startLine;
    private int startColumn;
    private int currentLine;
    private int currentColumn;


    public InputRange(int startLine, int startColumn, int currentLine, int currentColumn) {
        this.startLine = startLine;
        this.startColumn = startColumn;
        this.currentLine = currentLine;
        this.currentColumn = currentColumn;
    }

    public InputRange(InputRange inputRange) {
        this.startLine = inputRange.getStartLine();
        this.startColumn = inputRange.getStartColumn();
        this.currentLine = inputRange.getCurrentLine();
        this.currentColumn = inputRange.getCurrentColumn();
    }

    public InputRange() {
        this.startLine = 0;
        this.startColumn = 0;
        this.currentLine = 0;
        this.currentColumn = 0;
    }

    public void moveColumn(int i){
        this.currentColumn = currentColumn + i;
    }

    public void moveLine(int i){
        this.currentLine = currentLine + i;
        this.currentColumn = 0;
    }


    public InputRange reset(){
        return new InputRange(currentLine, currentColumn+1, currentLine, currentColumn+1);
    }

    public InputRange newPointFromCurrent(){
        return new InputRange(currentLine, currentColumn, currentLine, currentColumn);
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public void setStartColumn(int startColumn) {
        this.startColumn = startColumn;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getStartColumn() {
        return startColumn;
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }
}
