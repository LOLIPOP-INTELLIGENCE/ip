// package main.java.com.appleaster.main;

// import main.java.com.appleaster.command.Command;
// import main.java.com.appleaster.command.CommandType;
// import main.java.com.appleaster.exception.AppleasterException;
// import main.java.com.appleaster.parser.Parser;
// import main.java.com.appleaster.storage.Storage;
// import main.java.com.appleaster.task.TaskList;
// import main.java.com.appleaster.ui.Ui;

// /**
//  * The main class for the Appleaster application.
//  * This class initializes the application and handles the main interaction loop.
//  */

// public class Appleaster {
//   private final Storage storage;
//   private TaskList tasks;
//   private final Ui ui;

//     /**
//      * Constructs an Appleaster instance with the specified file path for storage.
//      *
//      * @param filePath The file path where tasks will be stored and loaded from.
//      */
//     public Appleaster(String filePath) {
//         ui = new Ui();
//         storage = new Storage(filePath);
//         try {
//             tasks = new TaskList(storage.load());
//         } catch (AppleasterException e) {
//             ui.showLoadingError();
//             tasks = new TaskList();
//         }
//     }

//     /**
//      * Starts the main interaction loop of the application.
//      * This method continuously processes user input until the exit command is given.
//      */
//     public void run() {
//         ui.showWelcome();
//         boolean isExit = false;
//         while (!isExit) {
//             String fullCommand = ui.readCommand();
//             ui.showLine();
//             try {
//                 Command c = Parser.parseCommand(fullCommand);
//                 isExit = executeCommand(c);
//             } catch (AppleasterException e) {
//                 ui.showError(e.getMessage());
//             }
//             ui.showLine();
//         }
//     }

//     /**
//      * Executes a command based on the parsed user input.
//      *
//      * @param c The Command object representing the user's input.
//      * @return true if the command is to exit the application, false otherwise.
//      * @throws AppleasterException If there's an error executing the command.
//      */
//     private boolean executeCommand(Command c) {
//         try {
//             switch (c.getType()) {
//                 case LIST:
//                     tasks.listTasks();
//                     break;
//                 case MARK:
//                 case UNMARK:
//                     tasks.markTask(c.getTaskIndex(), c.getType() == CommandType.MARK);
//                     break;
//                 case TODO:
//                 case DEADLINE:
//                 case EVENT:
//                     tasks.addTask(c.getTask());
//                     break;
//                 case DELETE:
//                     tasks.deleteTask(c.getTaskIndex());
//                     break;
//                 case DATE:
//                     tasks.listTasksOnDate(c.getDate());
//                     break;
//                 case FIND:
//                     tasks.displayMatchingTasks(c.getKeyword());
//                 break;                    
//                 case BYE:
//                     storage.save(tasks.getTasks());
//                     ui.showGoodbye();
//                     return true;
//             }
//         } catch (AppleasterException e) {
//             ui.showError(e.getMessage());
//         }
//         return false;
//     }

//     /**
//      * The main entry point of the application.
//      *
//      * @param args Command line arguments (not used).
//      */    
//     public static void main(String[] args) {
//         new Appleaster("data/tasks.txt").run();
//     }
// }

package main.java.com.appleaster.main;

import main.java.com.appleaster.gui.MainApplication;

public class Appleaster {
    public static void main(String[] args) {
        MainApplication.main(args);
    }
}