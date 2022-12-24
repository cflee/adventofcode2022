import java.util.*;
import java.util.regex.*;

public class Day15 {
    public static void main(final String[] args) throws Exception {
        //final List<String> input = Utils.readFile("15-sample.txt");
        //part1(input, 10);
        //part2(input, 20);
        final List<String> input2 = Utils.readFile("15.txt");
        //part1(input2, 2000000);
        part2(input2, 4000000);
    }

    final static Character SENSOR = 'S';
    final static Character BEACON = 'B';
    final static Character EMPTY = '#';

    static int dist(final int x1, final int y1, final int x2, final int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

	static class Coord {
        int x;
        int y;
        public Coord(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
        @Override public String toString() {
            return "Coord{x=" + x + ",y=" + y + "}";
        }
        @Override public int hashCode() {
            return Objects.hash((Integer) x, (Integer) y);
        }
        @Override public boolean equals(final Object obj) {
            if (obj instanceof Coord) {
                final Coord that = (Coord) obj;
                return x == that.x && y == that.y;
            }
            return false;
        }
	}

    static class Sensor {
        int x;
        int y;
        int beaconX;
        int beaconY;
        int beaconDist;
        public Sensor(final int x, final int y, final int beaconX, final int beaconY) {
            this.x = x;
            this.y = y;
            this.beaconX = beaconX;
            this.beaconY = beaconY;
            this.beaconDist = Day15.dist(x, y, beaconX, beaconY);
        }
        @Override public String toString() {
            return "Sensor{x=" + x + ",y=" + y + ",beacon x=" + beaconX + ",y=" + beaconY + ",dist=" + beaconDist + "}";
        }
    }

    static List<Sensor> parse(final List<String> input) {
        List<Sensor> sensors = new ArrayList<>();
        // Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        Pattern p = Pattern.compile("^Sensor at x=([\\d-]+?), y=([\\d-]+?): closest beacon is at x=([\\d-]+?), y=([\\d-]+?)$");
        for (String line : input) {
            Matcher m = p.matcher(line);
            m.matches();
            Sensor sensor = new Sensor(
                        Integer.parseInt(m.group(1)),
                        Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3)),
                        Integer.parseInt(m.group(4)));
            sensors.add(sensor);
        }
        return sensors;
    }

    static void part1(final List<String> input, final int targetY) {
        List<Sensor> sensors = parse(input);
		Map<Coord, Character> world = new HashMap<>();
        for (Sensor sensor : sensors) {
            if (Math.abs(sensor.y - targetY) > sensor.beaconDist) {
                // both the sensor and its closest beacon are too far away to influence
                continue;
            }
            // sensor or beacon always override any existing item
            if (sensor.y == targetY) {
                world.put(new Coord(sensor.x, sensor.y), SENSOR);
            }
            if (sensor.beaconY == targetY) {
                world.put(new Coord(sensor.beaconX, sensor.beaconY), BEACON);
            }
            for (int i = sensor.x - sensor.beaconDist; i <= sensor.x + sensor.beaconDist; i++) {
                Coord c = new Coord(i, targetY);
                if (world.containsKey(c)) {
                    continue;
                }
                if (dist(i, targetY, sensor.x, sensor.y) <= sensor.beaconDist) {
                    world.put(c, EMPTY);
                }
            }
        }
        int emptyCount = 0;
        for (Map.Entry<Coord, Character> entry : world.entrySet()) {
            if (entry.getValue() == EMPTY) {
                emptyCount += 1;
            }
        }
        System.out.println(emptyCount);
    }

    // +--> x
    // |
    // v y
    static void part2(final List<String> input, final int limit) {
        List<Sensor> sensors = parse(input);
row:
        for (int j = 0; j <= limit; j++) {
cell:
            for (int i = 0; i <= limit; i++) {
sensor:
                for (Sensor sensor : sensors) {
                    int distance = dist(sensor.x, sensor.y, i, j);
                    // other sensors may be close enough to be relevant
                    if (distance > sensor.beaconDist) {
                        continue sensor;
                    }
                    // use this sensor to skip cursor to end of its range within this row
                    // calculate an absolute position as cur may be "to the right" so i += is wrong
                    i = sensor.x + (sensor.beaconDist - Math.abs(sensor.y - j));
                    continue cell;
                }
                System.out.println(i * 4000000L + j);
                break row;
            }
        }
    }
}
