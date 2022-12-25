import java.util.*;

public class Day20 {
    public static void main(final String[] args) throws Exception {
        final List<String> input = Utils.readFile(
                //"20-sample.txt"
                "20.txt"
                );
        part2(input);
    }

    static class Node {
        Node prev;
        Node next;
        long data;
        public Node(final long data) {
            this.data = data;
        }
        @Override public String toString() {
            return "Node{data=" + data + ",prev=" + prev.data + ",next=" + next.data + "}";
        }
    }

    static void part1(final List<String> input) {
        Node zero = null;
        List<Node> nodes = new ArrayList<>();
        for (String line : input) {
            Node node = new Node(Long.parseLong(line));
            nodes.add(node);
            if (node.data == 0) {
                zero = node;
            }
            if (nodes.size() > 1) {
                Node prev = nodes.get(nodes.size() - 2);
                prev.next = node;
                node.prev = prev;
            }
        }
        Node first = nodes.get(0);
        Node last = nodes.get(nodes.size() - 1);
        last.next = first;
        first.prev = last;

        for (Node cur : nodes) {
            Node curPrev = cur.prev;
            Node curNext = cur.next;
            curPrev.next = curNext;
            curNext.prev = curPrev;
            Node newPrev;
            Node newNext;
            if (cur.data > 0) {
                newNext = curNext;
                for (long i = 0; i < cur.data; i++) {
                    newNext = newNext.next;
                }
                newPrev = newNext.prev;
            } else {
                newPrev = curPrev;
                for (long i = 0; i > cur.data; i--) {
                    newPrev = newPrev.prev;
                }
                newNext = newPrev.next;
            }
            cur.prev = newPrev;
            cur.next = newNext;
            newPrev.next = cur;
            newNext.prev = cur;
        }
        
        long sum = 0;
        Node cur = zero;
        for (int i = 0; i < 3001; i++) {
            if (i % 1000 == 0 && i != 0) {
                sum += cur.data;
            }
            cur = cur.next;
        }
        System.out.println(sum);
    }

    static void part2(final List<String> input) {
        Node zero = null;
        List<Node> nodes = new ArrayList<>();
        for (String line : input) {
            Node node = new Node(Long.parseLong(line) * 811589153L);
            nodes.add(node);
            if (node.data == 0) {
                zero = node;
            }
            if (nodes.size() > 1) {
                Node prev = nodes.get(nodes.size() - 2);
                prev.next = node;
                node.prev = prev;
            }
        }
        Node first = nodes.get(0);
        Node last = nodes.get(nodes.size() - 1);
        last.next = first;
        first.prev = last;

        for (int r = 0; r < 10; r++) {
            for (Node cur : nodes) {
                Node curPrev = cur.prev;
                Node curNext = cur.next;
                curPrev.next = curNext;
                curNext.prev = curPrev;
                Node newPrev;
                Node newNext;
                if (cur.data > 0) {
                    newNext = curNext;
                    for (long i = 0; i < (cur.data % (nodes.size() - 1)); i++) {
                        newNext = newNext.next;
                    }
                    newPrev = newNext.prev;
                } else {
                    newPrev = curPrev;
                    for (long i = 0; i > (cur.data % (nodes.size() - 1)); i--) {
                        newPrev = newPrev.prev;
                    }
                    newNext = newPrev.next;
                }
                cur.prev = newPrev;
                cur.next = newNext;
                newPrev.next = cur;
                newNext.prev = cur;
            }
        }

        long sum = 0;
        Node cur = zero;
        for (int i = 0; i < 3001; i++) {
            if (i % 1000 == 0 && i != 0) {
                sum += cur.data;
            }
            cur = cur.next;
        }
        System.out.println(sum);
    }
}
