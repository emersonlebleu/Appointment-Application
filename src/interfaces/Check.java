package interfaces;

/** Functional interface for the alert functionality. */
@FunctionalInterface
public interface Check {
    void popUp(Type type, String title, String message);
    enum Type { ERROR, CONFIRMATION }
}
