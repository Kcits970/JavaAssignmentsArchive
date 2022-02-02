public class Population {
    private Student[] students;
    private int currentSize;
    private String[] subjects;

    public Population(int size, String[] subjects) {
        students = new Student[size];
        this.subjects = subjects;
    }

    public Student getStudent(int studentIndex) {
        return students[studentIndex];
    }
    public int getCurrentSize() {return currentSize;}
    public String[] getSubjects() {return subjects;}

    public boolean add(Student s) {
        if (currentSize == students.length) return false;
        students[currentSize++] = s;
        return true;
    }

    public double mean(int subjectIndex) {
        double total = 0;
        for (Student s : students) {
            total += s.getScore(subjectIndex);
        }

        return total / currentSize;
    }

    public double variance(int subjectIndex) {
        double squareTotal = 0;
        for (Student s : students) {
            squareTotal += Math.pow(s.getScore(subjectIndex), 2);
        }

        //return E(X^2) - E(X)^2
        return (squareTotal / currentSize) - Math.pow(mean(subjectIndex), 2);
    }

    public double stdDeviation(int subjectIndex) {
        return Math.sqrt(variance(subjectIndex));
    }
}
