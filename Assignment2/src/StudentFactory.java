public class StudentFactory {
    public static Student createStudent(String details, int expected) {
        //constructs Student from given detail, if exception occurs during the parse, null is returned.
        String[] detailArray = details.split(",");

        String name = detailArray[0].trim();

        //each detail must contain at least the expected number of arguments
        if (detailArray.length - 1 < expected) {
            System.err.printf("'%s' contains less arguments than the expected %d\n", name, expected);
            return null;
        }

        int arraySize = detailArray.length - 1;
        int[] scores = new int[arraySize];

        for (int i = 0; i < arraySize; i++) {
            String currentValue = detailArray[i + 1].trim();
            try {
                scores[i] = Integer.parseInt(currentValue);
            } catch (NumberFormatException e) {
                //details may contain unparsable values
                System.err.printf("'%s' is not a valid argument to define the score of '%s'\n", currentValue, name);
                return null;
            }
        }

        return new Student(name, scores);
    }
}
