public class FindIntersectionNode {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // O(m+n) time, O(1) extra space.
    // Checks node identity (shared reference), not equal data values.
    static Node findIntersection(Node a, Node b) {
        int lengthA = length(a);
        int lengthB = length(b);

        while (lengthA > lengthB) {
            a = a.next;
            lengthA--;
        }
        while (lengthB > lengthA) {
            b = b.next;
            lengthB--;
        }

        while (a != b) {
            a = a.next;
            b = b.next;
        }
        return a;
    }

    private static int length(Node head) {
        int length = 0;
        while (head != null) {
            length++;
            head = head.next;
        }
        return length;
    }

    public static void main(String[] args) {
        Node shared = new Node(8);
        shared.next = new Node(4);
        shared.next.next = new Node(5);

        Node a = new Node(4);
        a.next = new Node(1);
        a.next.next = shared;

        Node b = new Node(5);
        b.next = new Node(6);
        b.next.next = new Node(1);
        b.next.next.next = shared;

        Node intersection = findIntersection(a, b);
        System.out.println(intersection == null ? "null" : "Node with value " + intersection.data);

        Node c = new Node(2);
        c.next = new Node(6);
        c.next.next = new Node(4);
        Node d = new Node(1);
        d.next = new Node(5);
        System.out.println(findIntersection(c, d)); // null
    }
}