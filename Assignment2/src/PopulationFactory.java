public class PopulationFactory {
    public static Population createPopulation(String filename) {
        VolatileStorage storage = new VolatileStorage();
        String[] subjects;

        try (UTF8Reader reader = UTF8Reader.getInstance(filename)) {
            //the first non-blank line is the header
            String header = reader.readLine();
            subjects = parseHeader(header);

            //constructs Student objects from each line, null-references are ignored
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                Student s = StudentFactory.createStudent(line, subjects.length);
                if (s != null) storage.add(s);
            }
        } catch (Exception e) {
            System.err.println("Exception occurred while constructing 'Population' object");
            System.err.println(e);
            return null;
        }

        //new Population object: previously constructed Student objects are added.
        Population p = new Population(storage.getSize(), subjects);
        for (StorageNode node = storage.getFirst(); node != null; node = node.getNext()) {
            Student s = (Student) node.getValue();
            p.add(s);
        }

        //clear the temporary storage, remaining StorageNode objects will be collected by GC
        storage.empty();
        return p;
    }

    public static String[] parseHeader(String header) throws Exception {
        if (header == null) {
            throw new InvalidHeaderException("");
        }

        String[] headerArray = header.split(",");
        int subjectAmount = 0;

        try {subjectAmount = Integer.parseInt(headerArray[0]);}
        catch (NumberFormatException e) {
            throw new InvalidHeaderException(headerArray[0]);
        }

        if (subjectAmount > headerArray.length - 1) {
            throw new InvalidHeaderException(subjectAmount);
        }
        String[] subjectArray = new String[subjectAmount];
        for (int i = 0; i < subjectAmount; i++) {
            subjectArray[i] = headerArray[i+1].trim();
        }

        return subjectArray;
    }
}
