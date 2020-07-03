import exception.InterpreterException;
import exception.ParserException;
import lexer.Lexer;
import lexer.LexerImpl;
import lexer.exception.IllegalInputException;
import lexer.streams.TextStream;
import lexer.streams.TextStreamImpl;
import lexer.token.Token;
import statement.Statement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PrintScript {
//    public static void main(String[] args) {
//        new CommandLine(new Tar()).execute(args);
//    }
    public static void main(String[] args) {
        Lexer lexer = new LexerImpl();
        Parser parser = new ParserImpl();

        String text;

        try {
            text = readFile(args);
            TextStream textStream = new TextStreamImpl(text);

            List<Token> output = lexer.lex(textStream);

            List<Statement> node = parser.parse(output);

            Interpreter interpreter = new InterpreterImpl();
            interpreter.interpret(node);

        }
        catch (IOException error) {
            System.out.println(error.getMessage());
        }
        catch (IllegalInputException error) {
            System.out.println(String.format("Invalid input: %s", error.getMessage()));
        }
        catch (ParserException error) {
            System.out.println(String.format("Syntax error: %s", error.getMessage()));
        }
        catch (InterpreterException error) {
            System.out.println(String.format("Runtime error: %s", error.getMessage()));
        }
        catch (Exception error) {
            System.out.println(String.format("Unknown error: %s", error.getMessage()));
        }

    }

    private static String readFile(String[] args) throws IOException {
        String filePath = "src\\main\\resources\\example.ts";
        if(args.length > 0) filePath = args[0];
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
