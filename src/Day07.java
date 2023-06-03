import java.util.*;
// import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 {
    static final String FILENAME = "./src/inputs/Day07.txt";

    static Map<String, String> ruleMap = Utils.readLines(FILENAME).stream().map(str ->
            str.substring(0, str.length() - 1).replace(" bags", "").replace(" bag", "")
    ).collect(Collectors.toMap(s -> s.split(" contain ")[0], s -> s.split(" contain ")[1]));

    static int part01() {
        var bagCount = 0;
        for (var key : ruleMap.keySet()) {
            if (hasShinyGold(key))
                bagCount++;
        }
        return bagCount;
    }

    static boolean hasShinyGold(String str) {
        if (ruleMap.get(str).contains("shiny gold"))
            return true;
        else {
            for (var value : ruleMap.get(str).split(", ")) {
                if (!value.equals("no other")) {
                    if (!hasShinyGold(value.substring(2)))
                        continue;
                    else
                        return true;
                }
            }
        }
        return false;
    }

    static int part02(String bagColor) {
        var totalBags = 0;
        for (var s : ruleMap.get(bagColor).split(", ")) {
            if (!s.equals("no other")) {
                var num = Integer.parseInt(s.substring(0, 1));
                totalBags += num + num * part02(s.substring(2));
            } else
                break;
        }
        return totalBags;
    }

    public static void main() {
        System.out.println("Advent of Code 2020, Day 07");
        System.out.println(part01());
        System.out.println(part02("shiny gold"));
    }

    // Alternate but slower part01
    /*static int part01() {
        var bagsWithSG = new ArrayList<String>();
        var queue = new ArrayDeque<String>(List.of("shiny gold"));
        while (!queue.isEmpty()) {
            var bag = queue.removeFirst();

            var newKeys = ruleMap.keySet().stream().filter(key -> Pattern.compile(".*" + bag + ".*").matcher(ruleMap.get(key)).find()).toList();
            for (var str : newKeys) {
                if (!bagsWithSG.contains(str)) {
                    bagsWithSG.add(str);
                    queue.addLast(str);
                }
            }
        }
        return bagsWithSG.size();
    }*/
}
