package HashMap_Implementation;

import java.util.*;

public class InfoExplorer {
    public static final String IMPLEMENTATION_TYPE = "HASHMAP_IMPLEMENTATION";
    public static final String LOAD_LOCATION = "info.txt";

    private Scanner inputScanner;
    private Map<String, Student> students;

    public InfoExplorer() {
        inputScanner = new Scanner(System.in);
        students = new LinkedHashMap<>();
    }

    private void printVersion() {
        System.out.printf("Student Info Explorer: %s\n\n", IMPLEMENTATION_TYPE);
    }

    private void loadStudents() {
        try (UTF8Reader reader = UTF8Reader.getInstance(LOAD_LOCATION)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Student currentStudent = Student.getInstanceFromString(line);
                if (currentStudent != null) students.put(currentStudent.getName(), currentStudent);
            }
        } catch (Exception e) {}
    }

    private void sortStudents(Comparator<? super Student> comparator) {
        students = Maps.sortByValues(students, comparator);
    }

    private void printAllStudents() {
        List<Object> studentInfo = new ArrayList<>();
        Collections.addAll(studentInfo, "Name", "Major", "Student ID", "Grade Point Average");
        for (Map.Entry<String, Student> mapping : students.entrySet()) {
            Student s = mapping.getValue();
            Collections.addAll(studentInfo, s.getName(), s.getMajor(), s.getID(), s.getGPA());
        }

        TablePrinter.printAsTable(studentInfo, 4, '|');
    }

    private void printStudent(Student s) {
        List<Object> studentInfo = new ArrayList<>();
        Collections.addAll(studentInfo
                , "Name", s.getName()
                , "Major", s.getMajor()
                , "Student ID", s.getID()
                , "Grade Point Average", s.getGPA());

        TablePrinter.printAsTable(studentInfo, 2, ':');
    }

    private Student findStudent(String name) {
        return students.get(name);
    }

    private String promptInput(String promptMessage) {
        System.out.print(promptMessage);
        return inputScanner.nextLine();
    }

    private void runFinder() {
        while (true) {
            String nameToFind = promptInput("Student name: ");
            if (nameToFind.equalsIgnoreCase("exit"))
                break;
            Student matchingStudent = findStudent(nameToFind);

            if (matchingStudent == null)
                System.out.printf("'%s' does not exist in the list.\n\n", nameToFind);
            else
                printStudent(matchingStudent);
        }
    }

    private void exit() {
        inputScanner.close();
        System.exit(0);
    }

    public void execute() {
        printVersion();
        loadStudents();
        sortStudents(Comparators.GPA_COMPARATOR);
        printAllStudents();

        runFinder();
        exit();
    }
}
