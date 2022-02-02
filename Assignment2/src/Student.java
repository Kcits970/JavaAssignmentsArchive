public class Student {
    private String name;
    private int[] scores;

    public Student(String name, int[] scores) {
        this.name = name;
        this.scores = scores;
    }

    public String getName() {return name;}
    public int getScore(int subjectIndex) {
        return scores[subjectIndex];
    }
}
