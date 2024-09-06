package main.java.com.appleaster.ui;
import java.util.Scanner;

public class Ui {
    private static final String LOGO = "   _____                .__                          __                \n"
                                     + "  /  _  \\ ______ ______ |  |   ____ _____    _______/  |_  ___________ \n"
                                     + " /  /_\\  \\\\____ \\\\____ \\|  | _/ __ \\\\__  \\  /  ___/\\   __\\/ __ \\_  __ \\\n"
                                     + "/    |    \\  |_> >  |_> >  |_\\  ___/ / __ \\_\\___ \\  |  | \\  ___/|  | \\/\n"
                                     + "\\____|__  /   __/|   __/|____/\\___  >____  /____  > |__|  \\___  >__|   \n"
                                     + "        \\/|__|   |__|             \\/     \\/     \\/            \\/       \n";

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("------------------------------------");
        System.out.println(LOGO);
        System.out.println("Greetings, human! Ready for some apple-solutely amazing conversation?");
        System.out.println("What can I do for you?");
        System.out.println("------------------------------------");
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with an empty task list.");
    }

    public void showLine() {
        System.out.println("------------------------------------");
    }

    public void showError(String message) {
        System.out.println("Oops! " + message);
        System.out.println("Please try again with a valid command.");
    }

    public void showGoodbye() {
        System.out.println("Goodbye! Remember, an apple a day keeps the doctor away, but I hope to see you sooner!");
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}