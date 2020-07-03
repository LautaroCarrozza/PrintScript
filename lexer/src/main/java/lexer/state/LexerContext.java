package lexer.state;

public class LexerContext {

    private String accum;
    private InputRange inputRange;

    public LexerContext() {
        this.accum = "";
        this.inputRange = new InputRange();
    }

    public LexerContext(String accum, InputRange inputRange) {
        this.accum = accum;
        this.inputRange = inputRange;
    }

    public String getAccum() {
        return accum;
    }

    public InputRange getInputRange() {
        return inputRange;
    }

    public InputRange resetRange(){
        return inputRange.reset();
    }


    public InputRange updateInputRange(Character c){
        InputRange updatedRange = new InputRange(inputRange);

        if(c.equals('\n')) {
            int newLine = updatedRange.getCurrentLine() + 1;
            updatedRange = new InputRange(newLine, 0, newLine, 0);
        }
        else if (c.equals('\t')) {
            updatedRange.moveColumn(4);
            updatedRange = updatedRange.reset();
        }
        else if (c.equals(' ')){
            updatedRange = updatedRange.reset();
        }
        else updatedRange.moveColumn(1);

        return updatedRange;
    }

    public InputRange moveBack() {
        InputRange newInputRange = new InputRange(inputRange);
        newInputRange.moveColumn(-1);
        return newInputRange;
    }
}
