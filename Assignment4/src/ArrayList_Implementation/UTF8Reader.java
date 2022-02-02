package ArrayList_Implementation;

import java.io.*;

public class UTF8Reader extends BufferedReader {
    private UTF8Reader(Reader in) {
        super(in);
    }

    public static UTF8Reader getInstance(String filename) throws IOException {
        FileInputStream stream;
        InputStreamReader reader;

        stream = new FileInputStream(filename);
        reader = new InputStreamReader(stream, "UTF-8");
        return new UTF8Reader(reader);
    }

    @Override
    public String readLine() throws IOException {
        String line;

        for(;;) {
            line = super.readLine();
            if (line == null || !line.isBlank()) break;
        }
        return line;
    }
}
