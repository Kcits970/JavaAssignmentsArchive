package ArrayList_Implementation;

import java.util.*;

public class InfoExplorer {
    private static final String IMPLEMENTATION_TYPE = "ARRAYLIST_IMPLEMENTATION";
    private static final String LOAD_LOCATION = "info.txt";

    private Scanner inputScanner;
    private List<Student> students;

    public InfoExplorer() {
        inputScanner = new Scanner(System.in);
        students = new ArrayList<>();
    }

    private void printVersion() {
        System.out.printf("Student Info Explorer: %s\n\n", IMPLEMENTATION_TYPE);
    }

    private void loadStudents() {
        try (UTF8Reader reader = UTF8Reader.getInstance(LOAD_LOCATION)) {
            for (String line = reader.readLine(); line != null; +line = reader.readLine()) {
                Student currentStudent = Student.getInstanceFromString(line);
                if (currentStudent != null) students.add(currentStudent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sortStudents(Comparator<? super Student> comparator) {
        Collections.sort(students, comparator);
    }

    private void printAllStudents() {
        List<Object> studentInfo = new ArrayList<>();
        Collections.addAll(studentInfo, "Name", "Major", "Student ID", "Grade Point Average");
        for (Student s : students) {
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
        Student dummy = new Student(name, null, 0, 0);
        int matchingIndex = Collections.binarySearch(students, dummy, Comparators.NAME_COMPARATOR);

        if (matchingIndex < 0) return null;
        else return students.get(matchingIndex);
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

        sortStudents(Comparators.NAME_COMPARATOR);
        runFinder();
        exit();
    }
}
