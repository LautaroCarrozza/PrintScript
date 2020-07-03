//import exception.InterpreterException;
//import exception.ParserException;
//import lexer.Lexer;
//import lexer.LexerImpl;
//import lexer.exception.IllegalInputException;
//import lexer.streams.TextStream;
//import lexer.streams.TextStreamImpl;
//import lexer.token.Token;
//import picocli.CommandLine;
//import statement.Statement;
//
//import java.io.*;
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.stream.Collectors;
//
//public class Tar implements Callable<Integer> {
//
//    @CommandLine.Option(names = { "-f", "--file" }, paramLabel = "ARCHIVE", description = "the archive file")
//    File archive;
//
//    @Override
//    public Integer call() throws Exception {
//        Lexer lexer = new LexerImpl();
//        Parser parser = new ParserImpl();
//        String text;
//
//        try {
//            text = readFile();
//
//            TextStream textStream = new TextStreamImpl(text);
//
//            List<Token> output = lexer.lex(textStream);
//
//            List<Statement> node = parser.parse(output);
//
//            Interpreter interpreter = new InterpreterImpl();
//            interpreter.interpret(node);
//
//        }
//        catch (IOException error) {
//            System.out.println(error.getMessage());
//            return 1;
//        }
//        catch (IllegalInputException error) {
//            System.out.println(String.format("Invalid input: %s", error.getMessage()));
//            return 1;
//        }
//        catch (ParserException error) {
//            System.out.println(String.format("Syntax error: %s", error.getMessage()));
//            return 1;
//        }
//        catch (InterpreterException error) {
//            System.out.println(String.format("Runtime error: %s", error.getMessage()));
//            return 1;
//        }
//        catch (Exception error) {
//            System.out.println(String.format("Unknown error: %s", error.getMessage()));
//            return 1;
//        }
//
//        return 0;
//    }
//
//    private String readFile() throws FileNotFoundException {
//        return new BufferedReader(new InputStreamReader(new FileInputStream(archive))).lines().collect(Collectors.joining("\n"));
//    }
//}
