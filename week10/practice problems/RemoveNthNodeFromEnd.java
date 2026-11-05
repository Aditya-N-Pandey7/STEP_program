public class RemoveNthNodeFromEnd {
    static class Node {
        int data;
        Node next;
        Node(int data) { this.data = data; }
    }

    // One pass: O(n) time, O(1) extra space.
    // Dummy node also handles removal of the original head.
    static Node removeNthFromEnd(Node head, int n) {
        Node dummy = new Node(0);
        dummy.next = head;
        Node fast = dummy;
        Node slow = dummy;

        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return dummy.next;
    }

    static void printList(Node head) {
        while (head != null) {
            System.out.print(head.data);
            if (head.next != null) System.out.print(" -> ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        printList(removeNthFromEnd(head, 2)); // 1 -> 2 -> 3 -> 5

        Node single = new Node(1);
        printList(removeNthFromEnd(single, 1)); // empty list
    }
}