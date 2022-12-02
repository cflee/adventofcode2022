import java.util.stream.Collectors;
import java.util.List;
import java.util.Optional;

public class Day01 {
    public static void main(String[] args) throws Exception {
        List<String> input = Utils.readFile(
                //"01-sample.txt"
                "01.txt"
                );
        part1(input);
    }

    static void part1(List<String> input) {
        List<List<String>> elfs = Utils.partitionByBlankLines(input);
        List<Integer> elfSums = elfs.stream()
            .map(elf -> elf.stream().map(Integer::valueOf).reduce(0, Integer::sum))
            .collect(Collectors.toList());
        Optional<Integer> optionalMax = elfSums.stream()
            .max(Integer::compare);
        System.out.println(optionalMax.get());
    }
}
