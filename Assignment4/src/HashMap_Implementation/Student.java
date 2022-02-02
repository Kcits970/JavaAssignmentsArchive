package HashMap_Implementation;

public class Student {
    private String name;
    private String major;
    private int id;
    private double gpa;

    public Student(String name, String major, int id, double gpa) {
        this.name = name;
        this.major = major;
        this.id = id;
        this.gpa = gpa;
    }

    public static Student getInstanceFromString(String details) {
        String[] detailArray = details.split(",");

        try {
            String name = detailArray[0].trim();
            String major = detailArray[1].trim();
            int id = Integer.parseInt(detailArray[2].trim());
            double gpa = Double.parseDouble(detailArray[3].trim());

            return new Student(name, major, id, gpa);
        } catch (Exception e) {
            return null;
        }
    }

    public String getName() {return name;}
    public String getMajor() {return major;}
    public int getID() {return id;}
    public double getGPA() {return gpa;}
}