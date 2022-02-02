public class Analyzer {
    private final Population population;
    private Object[][] table;

    public Analyzer(Population p) {
        population = p;
    }

    public void analyze() {
        String[] subjects = population.getSubjects();
        int rows = 4 + population.getCurrentSize();
        int columns = subjects.length + 1;
        table = new Object[rows][columns];

        //stores each value of into an Object array
        /* (table representation)
                  |    |    |    | (table[0])
                  ...
             E(X) |    |    |    | (table[rows - 3])
             V(X) |    |    |    | (table[rows - 2])
        \sigma(X) |    |    |    | (table[rows - 1])
         */
        table[0][0] = "";
        table[rows - 3][0] = "E(X)";
        table[rows - 2][0] = "V(X)";
        table[rows - 1][0] = "\u03C3(X)";

        for (int i = 0; i < population.getCurrentSize(); i++) {
            Student currentStudent = population.getStudent(i);
            table[i+1][0] = currentStudent.getName();
            for (int k = 1; k < columns; k++) {
                table[i+1][k] = currentStudent.getScore(k-1);
            }
        }

        for (int i = 0; i < subjects.length; i++) {
            table[0][i+1] = subjects[i];
            table[rows - 3][i+1] = String.format("%.4f", population.mean(i));
            table[rows - 2][i+1] = String.format("%.4f", population.variance(i));
            table[rows - 1][i+1] = String.format("%.4f", population.stdDeviation(i));
        }
    }

    public void printReport() {
        int rows = table.length;
        int columns = table[0].length;
        int[] longestSize = new int[columns];

        //finds the longest sized element in each column of 'table'
        for (int i = 0; i < columns; i++) {
            longestSize[i] = table[0][i].toString().length();

            for (int k = 1; k < rows; k++) {
                int elementSize = table[k][i].toString().length();
                if (longestSize[i] < elementSize)
                    longestSize[i] = elementSize;
            }
        }

        //StringBuilder object for mutable string operations
        StringBuilder builder = new StringBuilder();

        //prints each row, elements are separated by '|'
        for (Object[] objects : table) {
            for (int i = 0; i < columns; i++) {
                String element = objects[i].toString();
                int offset = longestSize[i] - element.length();

                //appends the current element to 'builder'
                //extra whitespace characters are added for text alignment
                builder.append(" ".repeat(offset));
                builder.append(element);

                if (i < columns - 1)
                    builder.append(" | ");
            }

            builder.append("\n");
        }

        System.out.printf("Statistics of %d student(s)\n", population.getCurrentSize());
        System.out.println(builder);
    }
}
