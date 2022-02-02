public class Launcher {
    public static void main(String[] args) {
        RecordManager manager = new RecordManager();
        MenuPrompt prompt = new MenuPrompt(manager);
        prompt.execute();
    }
}
