public class Day03 {
    static final String FILENAME = "./src/inputs/Day03.txt";

    static char[][] map = Utils.readLines(FILENAME).stream().map(s -> s.trim().toCharArray()).toArray(char[][]::new);

    static int treeCount(int right, int down) {
        var treeCount = 0;
        var i = 0;
        var j = 0;
        while (i < map.length - 1) {
            j += right;
            i += down;
            if (j >= map[0].length)
                j %= map[0].length;
            if (map[i][j] == '#')
                treeCount++;
        }
        return treeCount;
    }

    static int part01() {
        return treeCount(3, 1);
    }

    static int part02() {
        return treeCount(1, 1) * treeCount(3, 1) * treeCount(5, 1) * treeCount(7, 1) * treeCount(1, 2);
    }

    public static void main() {
        System.out.println("Advent of Code 2020, Day 03");
        System.out.println(part01());
        System.out.println(part02());
    }
}
