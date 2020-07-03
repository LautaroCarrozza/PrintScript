package errors;

public interface ErrorReporter {
    void report(int line, String where, String message);
    boolean hadError();
}
