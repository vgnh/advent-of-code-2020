fun main() {
    val startTime = System.nanoTime()

    day06.main()

    println("\nTime taken: ${String.format("%.6f", (System.nanoTime()-startTime)/1_000_000_000f)} seconds")
}
