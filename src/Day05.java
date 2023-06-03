import java.util.Arrays;
// import java.util.Collections;
import java.util.List;
// import java.util.stream.Collectors;

public class Day05 {
    static final String FILENAME = "./src/inputs/Day05.txt";

    static List<String> seatList = Utils.readLines(FILENAME);

    static boolean[] occupiedList = new boolean[((127 * 8) + 7) + 1]; // 0-1023, total = 1024, init to false

    static {
        Arrays.fill(occupiedList, false);
    }

    static int part01() {
        var highestSeatId = 0;
        for (var seat : seatList) {
            var low = 0;
            var up = 127;
            for (var ch : seat.substring(0, 7).toCharArray()) {
                if (ch == 'F')
                    up = (int) Math.floor((low + up) / 2.0);
                else if (ch == 'B')
                    low = (int) Math.ceil((low + up) / 2.0);
            }
            var row = seat.charAt(6) == 'F' ? low : up;

            low = 0;
            up = 7;
            for (var ch : seat.substring(7).toCharArray()) {
                if (ch == 'L')
                    up = (int) Math.floor((low + up) / 2.0);
                else if (ch == 'R')
                    low = (int) Math.ceil((low + up) / 2.0);
            }
            var col = seat.charAt(9) == 'L' ? low : up;

            var seatId = (row * 8) + col;
            occupiedList[seatId] = true; // for part02
            if (seatId > highestSeatId)
                highestSeatId = seatId;
        }
        return highestSeatId;
    }

    static int part02() {
        var skippedInitSeats = false;
        for (var i = 0; i < occupiedList.length; i++) {
            if (occupiedList[i] && !skippedInitSeats)
                skippedInitSeats = true;
            if (skippedInitSeats && !occupiedList[i])
                return i;
        }
        return -1;
    }

    public static void main() {
        System.out.println("Advent of Code 2020, Day 05");
        System.out.println(part01());
        System.out.println(part02());
    }

    // Alternate solution
    /*static List<Integer> seatList = Utils.readLines(FILENAME).stream().map(str ->
            Integer.parseInt(str.replace('F', '0').replace('B', '1').replace('L', '0').replace('R', '1'), 2)
    ).collect(Collectors.toList());

    static int part01() {
        return Collections.max(seatList);
    }

    static int part02() {
        Collections.sort(seatList);
        for (var i = 0; i < seatList.size() - 1; i++) {
            if (seatList.get(i + 1) - seatList.get(i) > 1) {
                return seatList.get(i) + 1;
            }
        }
        return -1;
    }*/
}
