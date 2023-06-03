public class Main {
    public static void main(String[] args) {
        var start = System.nanoTime();

        Day01.main();
        Day02.main();
        Day03.main();
        Day04.main();
        Day05.main();
        Day06.main();
        Day07.main();

        System.out.printf("\nTime elapsed: %.6fs\n", (System.nanoTime() - start) / 1_000_000_000f);
    }
}
