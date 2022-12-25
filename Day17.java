import java.util.*;
import java.util.stream.*;

public class Day17 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                "17-sample.txt"
                //"17.txt"
                );
        part2(input, 2022);
        part2(input, 1000000000000L);
    }

    static class Coord {
        long x;
        long y;
        public Coord(final long x, final long y) {
            this.x = x;
            this.y = y;
        }
        @Override public String toString() {
            return "Coord{x=" + x + ",y=" + y + "}";
        }
        @Override public int hashCode() {
            return Objects.hash((Long) x, (Long) y);
        }
        @Override public boolean equals(final Object obj) {
            if (obj instanceof Coord) {
                final Coord that = (Coord) obj;
                return x == that.x && y == that.y;
            }
            return false;
        }
    }

    static List<List<Coord>> rockShapes() {
        // a rock is a list of coords that describe the shape relative to a (0,0) that's left/bottom of stencil
        // and x grows up, y grows right
        List<List<Coord>> rockShapes = new ArrayList<>();
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(3, 0)));
        rockShapes.add(Arrays.asList(new Coord(1, 0), new Coord(0, 1), new Coord(1, 1), new Coord(2, 1), new Coord(1, 2)));
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0), new Coord(2, 1), new Coord(2, 2)));
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(0, 1), new Coord(0, 2), new Coord(0, 3)));
        rockShapes.add(Arrays.asList(new Coord(0, 0), new Coord(1, 0), new Coord(0, 1), new Coord(1, 1)));
        return rockShapes;
    }

    static List<Coord> materialiseRock(final Coord rockPos, final List<Coord> rockShape) {
        return rockShape.stream().map(c -> new Coord(c.x + rockPos.x, c.y + rockPos.y)).collect(Collectors.toList());
    }

    static boolean canFit(final List<Coord> rock, final Map<Coord, Character> space) {
        for (Coord c : rock) {
            if (c.x < 0 || c.x >= 7 || c.y < 0) {
                return false;
            }
            if (space.containsKey(c)) {
                return false;
            }
        }
        return true;
    }

    final static Character ROCK = '#';

    static void part1(final List<String> input) {
        String gases = input.get(0);
        List<List<Coord>> rockShapes = rockShapes();
        Map<Coord, Character> space = new HashMap<>();
        long gasCounter = 0;
        long maxY = -1;
        for (long r = 0; r < 2022; r++) {
            List<Coord> rockShape = rockShapes.get((int) (r % rockShapes.size()));
            Coord rockPos = new Coord(2, maxY + 3 + 1);
            while (true) {
                // attempt to impose the gas
                char gas = gases.charAt((int) (gasCounter % gases.length()));
                gasCounter += 1;
                if (gas == '>') {
                    Coord newRockPos = new Coord(rockPos.x + 1, rockPos.y);
                    List<Coord> rock = materialiseRock(newRockPos, rockShape);
                    if (canFit(rock, space)) {
                        rockPos = newRockPos;
                    }
                } else if (gas == '<') {
                    Coord newRockPos = new Coord(rockPos.x - 1, rockPos.y);
                    List<Coord> rock = materialiseRock(newRockPos, rockShape);
                    if (canFit(rock, space)) {
                        rockPos = newRockPos;
                    }
                }
                // attempt to fall one unit down
                Coord newRockPos = new Coord(rockPos.x, rockPos.y - 1);
                List<Coord> rock = materialiseRock(newRockPos, rockShape);
                if (canFit(rock, space)) {
                    rockPos = newRockPos;
                } else {
                    break;
                }
            }
            List<Coord> rock = materialiseRock(rockPos, rockShape);
            rock.stream().forEach(c -> space.put(c, ROCK));
            long newMaxY = rock.stream().mapToLong(c -> c.y).max().getAsLong();
            maxY = Math.max(maxY, newMaxY);
        }
        System.out.println(maxY + 1);
    }

    static class World {
        Map<Long, char[]> space;
        long minY;
        long maxY;
        public World() {
            space = new HashMap<>();
            minY = 0;
            maxY = -1;
        }
        public boolean contains(final long x, final long y) {
            if (!space.containsKey(y)) {
                return false;
            }
            return space.get(y)[(int) x] != '\u0000';
        }
        public void put(Coord coord1, Coord coord2, Character ch) {
            final long x = coord1.x + coord2.x;
            final long y = coord1.y + coord2.y;
            if (y < minY) {
                return;
            }
            char[] line;
            if (space.containsKey(y)) {
                line = space.get(y);
            } else {
                line = new char[7];
            }
            line[(int) x] = ch;
            space.put(y, line);
            maxY = Math.max(maxY, y);
        }
        public void prune() {
            Set<Integer> seenX = new HashSet<>();
            for (long y = maxY; y >= minY; y--) {
                if (!space.containsKey(y)) {
                    continue;
                }
                char[] line = space.get(y);
                for (int x = 0; x < 7; x++) {
                    if (line[x] != '\u0000') {
                        seenX.add(x);
                    }
                }
                if (seenX.size() == 7) {
                    for (long i = minY; i < y; i++) {
                        space.remove(i);
                    }
                    minY = y;
                    break;
                }
            }
        }
    }

    static boolean canFit(final long rockPosX, final long rockPosY, final List<Coord> rockShape, final World world) {
        for (Coord c : rockShape) {
            long rockX = rockPosX + c.x;
            long rockY = rockPosY + c.y;
            if (rockX < 0 || rockX > 6 || rockY < 0) {
                return false;
            }
            if (world.contains(rockX, rockY)) {
                return false;
            }
        }
        return true;
    }

    static void part2(final List<String> input, final long rockCount) {
        final String gases = input.get(0);
        final List<List<Coord>> rockShapes = rockShapes();
        final World world = new World();
        long gasCounter = 0;
        for (long r = 0; r < rockCount; r++) {
            if (r % 1000000 == 0) { System.out.println("r: " + r); }
            final List<Coord> rockShape = rockShapes.get((int) (r % rockShapes.size()));
            Coord rockPos = new Coord(2, world.maxY + 3 + 1);
            while (true) {
                // attempt to impose the gas
                final char gas = gases.charAt((int) gasCounter);
                gasCounter = (gasCounter + 1) % gases.length();
                if (gas == '>') {
                    if (canFit(rockPos.x + 1, rockPos.y, rockShape, world)) {
                        rockPos.x += 1;
                    }
                } else {
                    if (canFit(rockPos.x - 1, rockPos.y, rockShape, world)) {
                        rockPos.x -= 1;
                    }
                }
                // attempt to fall one unit down
                if (canFit(rockPos.x, rockPos.y - 1, rockShape, world)) {
                    rockPos.y -= 1;
                } else {
                    break;
                }
            }
            rockShape.stream().forEach(s -> world.put(s, rockPos, ROCK));
            if (r % 1000 == 0) {
                world.prune();
            }
        }
        System.out.println(world.maxY + 1);
    }
}
