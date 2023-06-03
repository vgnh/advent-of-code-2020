import java.util.List;

public class Day02 {
    static final String FILENAME = "./src/inputs/Day02.txt";

    static List<String> passwordPolicies = Utils.readLines(FILENAME);

    static class Policy {
        private final int max;
        private final int min;
        private final char letter;
        private final String password;

        Policy(String str) {
            var policyAndPass = str.split(": ");
            this.letter = policyAndPass[0].split(" ")[1].charAt(0);
            this.min = Integer.parseInt(policyAndPass[0].split(" ")[0].split("-")[0]);
            this.max = Integer.parseInt(policyAndPass[0].split(" ")[0].split("-")[1]);
            this.password = policyAndPass[1];
        }

        public int getMax() {
            return max;
        }

        public int getMin() {
            return min;
        }

        public char getLetter() {
            return letter;
        }

        public String getPassword() {
            return password;
        }
    }

    static int part01() {
        var validCount = 0;
        for (var str : passwordPolicies) {
            var policy = new Policy(str);
            var letterCount = 0;
            for (var ch : policy.getPassword().toCharArray()) {
                if (ch == policy.getLetter())
                    letterCount++;
            }
            if (letterCount >= policy.getMin() && letterCount <= policy.getMax())
                validCount++;
        }
        return validCount;
    }

    static int part02() {
        var validCount = 0;
        for (var str : passwordPolicies) {
            var policy = new Policy(str);
            if ((policy.getLetter() == policy.getPassword().charAt(policy.getMin() - 1)) ^ (policy.getLetter() == policy.getPassword().charAt(policy.getMax() - 1)))
                validCount++;
        }
        return validCount;
    }

    public static void main() {
        System.out.println("Advent of Code 2020, Day 02");
        System.out.println(part01());
        System.out.println(part02());
    }
}
