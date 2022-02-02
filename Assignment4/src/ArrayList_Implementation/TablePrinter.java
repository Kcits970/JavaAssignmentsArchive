package ArrayList_Implementation;

import java.util.List;

public class TablePrinter {
    private static int getObjectStringLength(Object o) {
        return o.toString().length();
    }

    private static int getNthColumnWidth(List<?> elements, int numOfColumns, int nth) {
        int greatestLength = 0;

        for (int i = nth; i < elements.size(); i += numOfColumns) {
            Object currentElement = elements.get(i);
            int currentElementLength = getObjectStringLength(currentElement);

            if (currentElementLength > greatestLength) {
                greatestLength = currentElementLength;
            }
        }

        return greatestLength;
    }

    private static int[] getColumnWidths(List<?> elements, int numOfColumns) {
        int[] widths = new int[numOfColumns];

        for (int n = 0; n < numOfColumns; n++) {
            widths[n] = getNthColumnWidth(elements, numOfColumns, n);
        }

        return widths;
    }

    private static void printPartitionCharacter(char partitionChar, int elementIndex, int numOfColumns) {
        if ((elementIndex + 1) % numOfColumns == 0)
            System.out.print("\n");
        else
            System.out.printf(" %c ", partitionChar);
    }

    public static void printAsTable(List<?> elements, int numOfColumns, char partitionChar) {
        int[] columnWidths = getColumnWidths(elements, numOfColumns);

        for (int i = 0; i < elements.size(); i++) {
            Object currentElement = elements.get(i);
            int offset = columnWidths[i % numOfColumns] - getObjectStringLength(currentElement);

            System.out.print(currentElement);
            System.out.print(" ".repeat(offset));
            printPartitionCharacter(partitionChar, i, numOfColumns);
        }

        System.out.println();
    }
}
