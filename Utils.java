import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    static List<String> readFile(final String filename) throws IOException {
        return Files.readAllLines(Paths.get(filename));
    }

    static List<List<String>> partitionByBlankLines(final List<String> input) {
        List<List<String>> result = new ArrayList<>();
        int prev = 0;
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            if (line.length() == 0) {
                result.add(input.subList(prev, i));
                prev = i + 1;
            }
            if (i == input.size() - 1 && prev != i + 1) {
                result.add(input.subList(prev, input.size()));
            }
        }
        return result;
    }
}
