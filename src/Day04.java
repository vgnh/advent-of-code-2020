import java.util.Arrays;
import java.util.regex.Pattern;

public class Day04 {
    static final String FILENAME = "./src/inputs/Day04.txt";

    static String[] passportList = Arrays.stream(Utils.readString(FILENAME).split("\n\n")).map(str ->
            String.join(" ", str.split("\n"))
    ).toArray(String[]::new);

    static int part01() {
        var validPass = 0;
        var pattern = Pattern.compile("(?=.*byr:.*)(?=.*iyr:.*)(?=.*eyr:.*)(?=.*hgt:.*)(?=.*hcl:.*)(?=.*ecl:.*)(?=.*pid:.*)");
        for (var passport : passportList) {
            if (pattern.matcher(passport).find())
                validPass++;
        }
        return validPass;
    }

    static int part02() {
        var validPass = 0;
        // byr = "19[2-9][0-9]|200[0-2]"
        // iyr = "20(1[0-9]|20)"
        // eyr = "20(2[0-9]|30)"
        // hgt = "(1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-3])in)"
        // hcl = "#[0-9a-f]{6}"
        // ecl = "(amb|blu|brn|gry|grn|hzl|oth)"
        // pid = "[0-9]{9}"
        var pattern =
                Pattern.compile("(?=.*byr:(19[2-9][0-9]|200[0-2]).*)(?=.*iyr:(20(1[0-9]|20)).*)(?=.*eyr:(20(2[0-9]|30)).*)(?=.*hgt:((1([5-8][0-9]|9[0-3])cm)|((59|6[0-9]|7[0-3])in)).*)(?=.*hcl:(#[0-9a-f]{6}).*)(?=.*ecl:(amb|blu|brn|gry|grn|hzl|oth).*)(?=.*pid:([0-9]{9}).*)");
        for (var passport : passportList) {
            if (pattern.matcher(passport).find())
                validPass++;
        }
        return validPass;
    }

    public static void main() {
        System.out.println("Advent of Code 2020, Day 04");
        System.out.println(part01());
        System.out.println(part02());
    }
}
