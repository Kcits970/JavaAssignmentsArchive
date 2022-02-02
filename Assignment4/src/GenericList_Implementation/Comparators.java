package GenericList_Implementation;

import java.util.Comparator;

public class Comparators {
    public static final Comparator<Student> GPA_COMPARATOR;
    public static final Comparator<Student> NAME_COMPARATOR;

    static {
        GPA_COMPARATOR = new GPAComparator();
        NAME_COMPARATOR = new NameComparator();
    }
}

class GPAComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Double.compare(o1.getGPA(), o2.getGPA());
    }
}

class NameComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return CharSequence.compare(o1.getName(), o2.getName());
    }
}