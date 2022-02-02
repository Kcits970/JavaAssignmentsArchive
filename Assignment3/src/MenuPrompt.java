import java.util.Scanner;

public class MenuPrompt {
    private static final int VERSION_ID = 2;
    private static final int MENU_LENGTH = 30;
    private final RecordManager manager;
    private final Scanner scanner;

    public MenuPrompt(RecordManager m) {
        manager = m;
        scanner = new Scanner(System.in);
    }

    private void formatOptions(String option, char command) {
        //formats the given menu for alignment
        //offset lengths are filled with '-' (dash) characters
        int offset = MENU_LENGTH - option.length();
        System.out.printf("%S %s %c\n", option, "-".repeat(offset), command);
    }

    private void printMenuTitle(String menu) {
        System.out.printf("\t<%S>\n", menu);
    }

    private void printMainOptions() {
        printMenuTitle("main options");
        formatOptions("display current records", 'A');
        formatOptions("register new student", 'B');
        formatOptions("register new item", 'C');
        formatOptions("load from file", 'D');
        formatOptions("save to file", 'E');
        formatOptions("exit", 'F');
    }

    private String promptRegistration(int required) {
        //prompts the user to input registration information
        System.out.printf("Required argument format: \"name,score,score,score,...\"\n");
        System.out.printf("Required minimum: %d arguments (1 + %d)\n", required + 1, required);
        System.out.print(">>");
        return scanner.nextLine();
    }

    private String promptFilename() {
        //prompts the user to input filename information
        System.out.println("Enter a valid filename: (default filename will be used if the input is blank)");
        System.out.print(">>");

        String filename = scanner.nextLine();
        if (filename.isBlank()) return null;
        return filename.trim();
    }

    private void registerStudent() {
        printMenuTitle("register new student");
        int required = manager.getItemSize();
        manager.addStudent(promptRegistration(required));
        System.out.println();
    }

    private void registerItem() {
        printMenuTitle("register new item");
        int required = manager.getStudentSize();
        manager.addNewItem(promptRegistration(required));
        System.out.println();
    }

    private void save() {
        printMenuTitle("save to file");
        manager.save(promptFilename());
        System.out.println();
    }
    private void load() {
        printMenuTitle("load from file");
        manager.load(promptFilename());
        System.out.println();
    }

    private void display() {
        printMenuTitle("display current records");
        manager.printRecords();
        System.out.println();
    }

    public void execute() {
        System.out.printf("Record Manager Prompt: Version %d\n\n", VERSION_ID);

        boolean exit = false;
        char selection;
        while (!exit) {
            printMainOptions();
            System.out.print(">>");
            selection = scanner.nextLine().charAt(0);

            switch (selection) {
                case 'A': //display current records
                    display();
                    break;
                case 'B': //register new student
                    registerStudent();
                    break;
                case 'C': //register new item
                    registerItem();
                    break;
                case 'D': //load from file
                    load();
                    break;
                case 'E': //save to file
                    save();
                    break;
                case 'F': //exit
                    exit = true;
                    break;
                default:
                    System.out.printf("'%c' is not a valid option\n", selection);
            }
        }
    }
}
