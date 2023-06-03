import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Day06 {
    static final String FILENAME = "./src/inputs/Day06.txt";

    static List<String> groupList = Arrays.stream(Utils.readString(FILENAME).split("\n\n")).map(str ->
            String.join(" ", str.split("\n"))
    ).collect(Collectors.toList());

    static int part01() {
        var totalYesCount = 0;
        for (var group : groupList) {
            var answerSet = new HashSet<Character>();
            for (var answer : group.split(" "))
                answerSet.addAll(answer.chars().mapToObj(c -> (char) c).toList());
            totalYesCount += answerSet.size();
        }
        return totalYesCount;
    }

    static int part02() {
        var totalYesCount = 0;
        for (var group : groupList) {
            HashSet<Character> answerSet = null;
            for (var answer : group.split(" ")) {
                if (answerSet == null)
                    answerSet = answer.chars().mapToObj(c -> (char) c).collect(Collectors.toCollection(HashSet::new));
                else
                    answerSet.retainAll(answer.chars().mapToObj(c -> (char) c).toList());
            }
            totalYesCount += answerSet.size();
        }
        return totalYesCount;
    }

    public static void main() {
        System.out.println("Advent of Code 2020, Day 06");
        System.out.println(part01());
        System.out.println(part02());
    }
}
