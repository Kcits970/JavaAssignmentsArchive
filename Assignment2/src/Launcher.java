public class Launcher {
    public static void main(String[] args) {
        Population p = PopulationFactory.createPopulation("scores_kor_eng.txt");
        if (p == null) return;

        Analyzer analyzer = new Analyzer(p);
        analyzer.analyze();
        analyzer.printReport();
    }
}
