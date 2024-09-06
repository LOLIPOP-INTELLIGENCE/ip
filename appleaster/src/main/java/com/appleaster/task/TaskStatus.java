package main.java.com.appleaster.task;
public enum TaskStatus {
    NOT_DONE(" "),
    DONE("X");

    private final String symbol;

    TaskStatus(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}