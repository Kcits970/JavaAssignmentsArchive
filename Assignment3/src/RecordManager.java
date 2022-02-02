public class RecordManager {
    private static final int MAX_STUDENTS = 40;
    private static final int MAX_ITEMS = 10;
    private static final String DEFAULT_PATH = "data.txt";

    private int noOfItems;
    private int noOfStudents;
    private String[] names;
    private String[] items;
    private int[][] scores;
    private double[] avgScores;

    public RecordManager() {
        noOfItems = 0;
        noOfStudents = 0;
        names = new String[MAX_STUDENTS];
        items = new String[MAX_ITEMS];
        scores = new int[MAX_STUDENTS][MAX_ITEMS];
        avgScores = new double[MAX_ITEMS];
    }

    public int getStudentSize() {return noOfStudents;}
    public int getItemSize() {return noOfItems;}

    public boolean addStudent(String line) {
        //return false if max capacity is reached
        if (noOfStudents == MAX_STUDENTS) {
            System.err.printf("Max capacity reached: cannot assign more than %d\n", MAX_STUDENTS);
            return false;
        }

        //return false if given line is null
        if (line == null) return false;

        String[] details = line.split(",");
        names[noOfStudents] = details[0].trim();

        int i = 0;
        try {
            for (; i < noOfItems; i++)
                scores[noOfStudents][i] = Integer.parseInt(details[i+1].trim());
            noOfStudents++;
        } catch (Exception e) {
            if (i+1 < details.length)
                System.err.printf("Invalid argument: unexpected '%s' at position %d\n", details[i+1], i+1);
            else
                System.err.printf("Invalid argument: reached end of line at position %d\n", i+1);
            return false;
        }

        return true;
    }

    public boolean addItem(String item) {
        if (noOfItems == MAX_ITEMS) return false;
        items[noOfItems++] = item;
        return true;
    }

    public boolean addNewItem(String line) {
        //return false if max capacity is reached
        if (noOfItems == MAX_ITEMS) {
            System.err.printf("Max capacity reached: cannot assign more than %d\n", MAX_ITEMS);
            return false;
        }

        //return false if given line is null
        if (line == null) return false;

        String[] details = line.split(",");
        items[noOfItems] = details[0].trim();

        int i = 0;
        try {
            for (; i < noOfStudents; i++)
                scores[i][noOfItems] = Integer.parseInt(details[i+1].trim());
            noOfItems++;
        } catch (Exception e) {
            if (i+1 < details.length)
                System.err.printf("Invalid argument: unexpected '%s' at position %d\n", details[i+1], i+1);
            else
                System.err.printf("Invalid argument: reached end of line at position %d\n", i+1);
            return false;
        }

        return true;
    }

    public boolean load(String filename) {
        if (filename == null) filename = DEFAULT_PATH;

        //reset the current size
        noOfItems = 0;
        noOfStudents = 0;

        try (UTF8Reader reader = UTF8Reader.getInstance(filename)) {
            reader.readLine(); //read off the redundant metadata
            String[] header = reader.readLine().split(",");

            for (int i = 0; i < header.length; i++)
                if (!addItem(header[i])) break;

            for (String line = reader.readLine(); line != null; line = reader.readLine())
                if (!addStudent(line)) break;

            System.out.printf("Load from \"%s\" complete\n", filename);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public boolean save(String filename) {
        if (filename == null) filename = DEFAULT_PATH;

        try (UTF8Writer writer = UTF8Writer.getInstance(filename)) {
            StringBuilder whole = new StringBuilder();

            //appending the first header
            whole.append(noOfItems + "," + noOfStudents + "\n");

            //appending subject details
            for (int i = 0; i < noOfItems; i++) {
                whole.append(items[i]);
                if (i < noOfItems - 1) whole.append(",");
            }
            whole.append("\n");

            //appending student details
            for (int i = 0; i < noOfStudents; i++) {
                whole.append(names[i]);
                for (int k = 0; k < noOfItems; k++) {
                    whole.append("," + scores[i][k]);
                }
                whole.append("\n");
            }

            writer.append(whole);
            writer.flush();
            System.out.printf("Save to \"%s\" complete\n", filename);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }

        return true;
    }

    public void computeAverage(){
        double sum;
        for(int i = 0; i < noOfItems; i++) {
            sum = 0;
            for (int j = 0; j < noOfStudents; j++) {
                sum = sum + scores[j][i];
            }
            avgScores[i] = sum / noOfStudents;
        }
    }

    private Object[][] getDataTable() {
        //stores each value of into an Object array
        /* (table format)
             | item1 | item2 | item3
        name |       |       |
        ...  |       |       |
        Mean |       |       |
         */

        //calculate the average first, new elements may be appendedz
        computeAverage();

        int rows = noOfStudents + 2;
        int columns = noOfItems + 1;
        Object[][] table = new Object[rows][columns];

        table[0][0] = "(Data Table)";
        table[rows - 1][0] = "Mean";
        for (int i = 0; i < noOfStudents; i++) {
            table[i+1][0] = names[i];
            for (int k = 0; k < noOfItems; k++) {
                table[i+1][k+1] = scores[i][k];
            }
        }

        for (int i = 0; i < noOfItems; i++) {
            table[0][i+1] = items[i];
            table[rows - 1][i+1] = String.format("%.4f", avgScores[i]);
        }

        return table;
    }

    public void printRecords() {
        Object[][] table = getDataTable();
        int rows = table.length;
        int columns = table[0].length;
        int[] longest = new int[columns];

        //finds the longest sized element in each column of 'table'
        for (int i = 0; i < columns; i++) {
            longest[i] = table[0][i].toString().length();

            for (int k = 1; k < rows; k++) {
                int elementSize = table[k][i].toString().length();
                if (longest[i] < elementSize)
                    longest[i] = elementSize;
            }
        }

        //StringBuilder object for mutable string operations
        StringBuilder builder = new StringBuilder();

        //prints each row, elements are separated by '|'
        for (Object[] objects : table) {
            for (int i = 0; i < columns; i++) {
                String element = objects[i].toString();
                int offset = longest[i] - element.length();

                //appends the current element to 'builder'
                //extra whitespace characters are added for text alignment
                builder.append(element);
                builder.append(" ".repeat(offset));

                if (i < columns - 1)
                    builder.append(" | ");
            }

            builder.append("\n");
        }

        //removing the trailing newline
        builder.deleteCharAt(builder.length() - 1);

        System.out.printf("Statistics of %d student(s)\n", noOfStudents);
        System.out.println(builder);
    }
}
