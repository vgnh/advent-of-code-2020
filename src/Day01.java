import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 {
    static final String FILENAME = "./src/inputs/Day01.txt";

    static List<Integer> expenseReport = Utils.readLines(FILENAME).stream().map(Integer::parseInt).collect(Collectors.toList());

    static int part01() {
        var expenseList = new ArrayList<Integer>();
        for (var expense : expenseReport) {
            var diff = Math.abs(2020 - expense);
            if (expenseList.contains(diff))
                return expense * diff;
            else
                expenseList.add(expense);
        }
        return -1;
    }

    static int part02() {
        for (var i = 0; i < expenseReport.size(); i++) {
            for (var j = i + 1; j < expenseReport.size(); j++) {
                var expenseOnePlusTwo = expenseReport.get(i) + expenseReport.get(j);
                var diff = 2020 - expenseOnePlusTwo;
                if (!(expenseOnePlusTwo < 2020) && (diff < 0))
                    continue;
                if (expenseReport.contains(diff))
                    return diff * expenseReport.get(i) * expenseReport.get(j);
            }
        }
        return -1;
    }

    public static void main() {
        System.out.println("Advent of Code 2020, Day 01");
        System.out.println(part01());
        System.out.println(part02());
    }
}
