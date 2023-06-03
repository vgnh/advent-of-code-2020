import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static List<String> readLines(String filename) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get(filename)).stream().map(String::trim).collect(Collectors.toList());
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return lines;
    }

    public static String readString(String filename) {
        var str = "";
        try {
            str = Files.readString(Paths.get(filename)).trim();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
        return str;
    }
}
