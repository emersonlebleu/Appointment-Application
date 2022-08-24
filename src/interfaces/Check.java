package interfaces;

@FunctionalInterface
public interface Check {
    void popUp(Type type, String title, String message);
    enum Type { ERROR, CONFIRMATION }
}
