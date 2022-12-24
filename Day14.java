import java.util.*;

public class Day14 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"14-sample.txt"
                "14.txt"
                );
        part2(input);
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
        public Iterator<Coord> iterTo(final Coord that) {
            final List<Coord> coords = new ArrayList<>();
            coords.add(this);
            if (this.x == that.x) {
                final int diff = Math.abs(that.y - this.y);
                final int dir = (that.y - this.y) / diff;
                for (int i = 1; i <= diff; i++) {
                    coords.add(new Coord(this.x, this.y + (i * dir)));
                }
            } else if (this.y == that.y) {
                final int diff = Math.abs(that.x - this.x);
                final int dir = (that.x - this.x) / diff;
                for (int i = 1; i <= diff; i++) {
                    coords.add(new Coord(this.x + (i * dir), this.y));
                }
            }
            return coords.iterator();
        }
    }

    static final Character ROCK = '#';
    static final Character SAND = 'o';
    static class World {
        Map<Coord, Character> space;
        int maxX;
        int maxY;
        public World() {
            space = new HashMap<>();
            maxX = 0;
            maxY = 0;
        }
        public void put(Coord coord, Character c) {
            if (c == ROCK) {
                if (coord.x > maxX) {
                    maxX = coord.x;
                }
                if (coord.y > maxY) {
                    maxY = coord.y;
                }
            }
            space.put(coord, c);
        }
    }

    static World parse(final List<String> input) {
        World world = new World();
        for (String pathString : input) {
            List<Coord> pathCoords = new ArrayList<>();
            
            String[] pathPoints = pathString.split(" -> ");
            for (String pathPoint : pathPoints) {
                String[] pathPointParts = pathPoint.split(",");
                pathCoords.add(new Coord(Integer.parseInt(pathPointParts[0]), Integer.parseInt(pathPointParts[1])));
            }
            for (int i = 1; i < pathCoords.size(); i++) {
                Coord prevCoord = pathCoords.get(i-1);
                Coord curCoord = pathCoords.get(i);
                for (Iterator<Coord> iter = prevCoord.iterTo(curCoord); iter.hasNext();) {
                    Coord nextCoord = iter.next();
                    world.put(nextCoord, ROCK);
                }
            }
        }
        return world;
    }

    // +--> x
    // |
    // v y
    static void part1(final List<String> input) {
        World world = parse(input);
sandOuter:
        for (int s = 1; ; s++) {
            Coord sand = new Coord(500, 0);
            while (true) {
                if (sand.x > world.maxX || sand.y > world.maxY) {
                    // sand has departed the world
                    // s is the unsuccessful one, the last one at rest is (s - 1)
                    System.out.println((s - 1));
                    break sandOuter;
                }
                if (!world.space.containsKey(new Coord(sand.x, sand.y + 1))) {
                    sand.y += 1;
                    continue;
                }
                if (!world.space.containsKey(new Coord(sand.x - 1, sand.y + 1))) {
                    sand.x -= 1;
                    sand.y += 1;
                    continue;
                }
                if (!world.space.containsKey(new Coord(sand.x + 1, sand.y + 1))) {
                    sand.x += 1;
                    sand.y += 1;
                    continue;
                }
                world.put(sand, SAND);
                continue sandOuter;
            }
        }
    }

    static void part2(final List<String> input) {
        World world = parse(input);
sandOuter:
        for (int s = 1; ; s++) {
            Coord sand = new Coord(500, 0);
            while (true) {
                if (sand.y == (world.maxY + 1)) {
                    // stop at 1 only as this is the spot where it's already resting
                    // on top of the virtual floor at (maxY + 2)
                    world.put(sand, SAND);
                    continue sandOuter;
                }
                if (!world.space.containsKey(new Coord(sand.x, sand.y + 1))) {
                    sand.y += 1;
                    continue;
                }
                if (!world.space.containsKey(new Coord(sand.x - 1, sand.y + 1))) {
                    sand.x -= 1;
                    sand.y += 1;
                    continue;
                }
                if (!world.space.containsKey(new Coord(sand.x + 1, sand.y + 1))) {
                    sand.x += 1;
                    sand.y += 1;
                    continue;
                }
                if (sand.x == 500 && sand.y == 0) {
                    world.put(sand, SAND);
                    // not (s-1) as this s'th is also at rest
                    System.out.println((s));
                    break sandOuter;
                }
                world.put(sand, SAND);
                continue sandOuter;
            }
        }
    }
}
