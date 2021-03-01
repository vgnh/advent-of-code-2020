fun main() {
    val startTime = System.nanoTime()

    day01.main()
    day02.main()
    day03.main()
    day04.main()
    day05.main()
    day06.main()

    println("\nTime taken: ${String.format("%.6f", (System.nanoTime()-startTime)/1_000_000_000f)} seconds")
}
